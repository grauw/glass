package nl.grauw.glass;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.directives.Directive;
import nl.grauw.glass.directives.Ds;
import nl.grauw.glass.directives.Equ;
import nl.grauw.glass.directives.If;
import nl.grauw.glass.directives.Incbin;
import nl.grauw.glass.directives.Include;
import nl.grauw.glass.directives.Instruction;
import nl.grauw.glass.directives.Irp;
import nl.grauw.glass.directives.Macro;
import nl.grauw.glass.directives.Proc;
import nl.grauw.glass.directives.Rept;
import nl.grauw.glass.directives.Section;
import nl.grauw.glass.directives.Terminator;
import nl.grauw.glass.expressions.Annotation;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Sequence;

public class SourceBuilder {
	
	public static final List<String> END_TERMINATORS = Arrays.asList(new String[] { "end", "END" });
	public static final List<String> ENDM_TERMINATORS = Arrays.asList(new String[] { "endm", "ENDM" });
	public static final List<String> ENDP_TERMINATORS = Arrays.asList(new String[] { "endp", "ENDP" });
	public static final List<String> ENDS_TERMINATORS = Arrays.asList(new String[] { "ends", "ENDS" });
	public static final List<String> ELSE_TERMINATORS = Arrays.asList(new String[] { "else", "ELSE", "endif", "ENDIF" });
	public static final List<String> ENDIF_TERMINATORS = Arrays.asList(new String[] { "endif", "ENDIF" });
	
	private final Source source;
	private final List<String> terminators;
	private final List<Path> includePaths;
	private final Parser parser = new Parser();
	
	private static final List<Path> sourcePaths = new ArrayList<Path>();
	
	public SourceBuilder(List<Path> includePaths) {
		this.source = new Source();
		this.terminators = END_TERMINATORS;
		this.includePaths = includePaths;
	}
	
	public SourceBuilder(Source source, List<String> terminators, List<Path> includePaths) {
		this.source = source;
		this.terminators = terminators;
		this.includePaths = includePaths;
	}
	
	public boolean hasLoadedSourceFile(Path path) {
		try {
			for (Path sourcePath : sourcePaths)
				if (Files.isSameFile(path, sourcePath))
					return true;
		} catch (IOException e) {
		}
		return false;
	}
	
	public Source parse(Path sourcePath) {
		sourcePaths.add(sourcePath);
		try {
			parse(Files.newInputStream(sourcePath), sourcePath);
		} catch (IOException e) {
			throw new AssemblyException(e);
		}
		return source;
	}
	
	private Source parseInclude(Expression sourcePath, Path basePath, boolean once) {
		Path fullPath = basePath.resolveSibling(sourcePath.getString());
		if (Files.exists(fullPath)) {
			if (once && hasLoadedSourceFile(fullPath))
				return null;
			return parse(fullPath);
		}
		return parseInclude(sourcePath, once);
	}
	
	private Source parseInclude(Expression sourcePath, boolean once) {
		for (Path includePath : includePaths) {
			Path fullPath = includePath.resolve(sourcePath.getString());
			if (Files.exists(fullPath)) {
				if (once && hasLoadedSourceFile(fullPath))
					return null;
				return parse(fullPath);
			}
		}
		throw new AssemblyException("Include file not found: " + sourcePath.getString());
	}
	
	public Source parse(InputStream reader, Path sourcePath) {
		return parse(new InputStreamReader(reader, Charset.forName("ISO-8859-1")), sourcePath);
	}
	
	public Source parse(Reader reader, Path sourcePath) {
		return parse(new LineNumberReader(reader), sourcePath);
	}
	
	private Source parse(LineNumberReader reader, Path sourcePath) {
		while (true) {
			Line line = parser.parse(reader, new Scope(source.getScope()), sourcePath);
			
			try {
				Directive directive = getDirective(line, reader, sourcePath);
				line.setDirective(directive);
				source.addLine(line);
				if (directive instanceof Terminator)
					return source;
			} catch (AssemblyException e) {
				e.addContext(line);
				throw e;
			}
		}
	}
	
	public Directive getDirective(Line line, LineNumberReader reader, Path sourcePath) {
		if (line.getMnemonic() == null)
			return new Instruction();
		
		switch (line.getMnemonic()) {
		case "equ":
		case "EQU":
			return new Equ();
		case "include":
		case "INCLUDE":
			return getIncludeDirective(line, sourcePath);
		case "incbin":
		case "INCBIN":
			return new Incbin(sourcePath, includePaths);
		case "macro":
		case "MACRO":
			return new Macro(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourcePath));
		case "rept":
		case "REPT":
			return new Rept(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourcePath));
		case "irp":
		case "IRP":
			return new Irp(parseBlock(line.getScope(), ENDM_TERMINATORS, reader, sourcePath));
		case "proc":
		case "PROC":
			return new Proc(parseBlock(line.getScope(), ENDP_TERMINATORS, reader, sourcePath));
		case "if":
		case "IF":
			Source thenBlock = parseBlock(source.getScope(), ELSE_TERMINATORS, reader, sourcePath);
			Source elseBlock = !ENDIF_TERMINATORS.contains(thenBlock.getLastLine().getMnemonic()) ?
					parseBlock(source.getScope(), ENDIF_TERMINATORS, reader, sourcePath) : null;
			return new If(thenBlock, elseBlock);
		case "section":
		case "SECTION":
			return new Section(parseBlock(source.getScope(), ENDS_TERMINATORS, reader, sourcePath));
		case "ds":
		case "DS":
			return new Ds();
		case "end":
		case "END":
		case "endm":
		case "ENDM":
		case "endp":
		case "ENDP":
		case "ends":
		case "ENDS":
		case "else":
		case "ELSE":
		case "endif":
		case "ENDIF":
			if (!terminators.contains(line.getMnemonic())) {
				if (line.getMnemonic() == "end" || line.getMnemonic() == "END")
					throw new AssemblyException("Unexpected end of file. Expecting: " + terminators.toString());
				throw new AssemblyException("Unexpected " + line.getMnemonic() + ".");
			}
			return new Terminator();
		default:
			return new Instruction();
		}
	}
	
	private Directive getIncludeDirective(Line line, Path sourcePath) {
		boolean once = false;
		Expression argument = line.getArguments();
		if (argument instanceof Annotation) {
			String annotation = argument.getAnnotation().getName();
			if ("once".equals(annotation) || "ONCE".equals(annotation)) {
				argument = argument.getAnnotee();
				once = true;
			}
		}
		if (line.getArguments() instanceof Sequence)
			throw new AssemblyException("Include only accepts 1 argument.");
		if (!argument.isString())
			throw new AssemblyException("A string literal is expected.");
		SourceBuilder sourceBuilder = new SourceBuilder(source, END_TERMINATORS, includePaths);
		sourceBuilder.parseInclude(argument, sourcePath, once);
		return new Include();
	}
	
	private Source parseBlock(Scope scope, List<String> terminators, LineNumberReader reader, Path sourcePath) {
		return new SourceBuilder(new Source(scope), terminators, includePaths).parse(reader, sourcePath);
	}
	
}
