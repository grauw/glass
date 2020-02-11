package nl.grauw.glass;

import java.io.IOException;
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
	
	private static final List<SourceFile> sourceFiles = new ArrayList<SourceFile>();
	
	public SourceBuilder(List<Path> includePaths) {
		this.source = new Source();
		this.terminators = END_TERMINATORS;
		this.includePaths = includePaths;
	}
	
	public SourceBuilder(Scope scope, List<String> terminators, List<Path> includePaths) {
		this.source = new Source(scope);
		this.terminators = terminators;
		this.includePaths = includePaths;
	}
	
	public boolean hasLoadedSourceFile(Path path) {
		try {
			for (SourceFile sourceFile : sourceFiles)
				if (Files.isSameFile(path, sourceFile.getPath()))
					return true;
		} catch (IOException e) {
		}
		return false;
	}
	
	private List<Path> getIncludePaths(SourceFile sourceFile) {
		List<Path> basePaths = new ArrayList<Path>();
		Path path = sourceFile.getPath();
		if (path != null)
			basePaths.add(path.resolveSibling(path.getFileSystem().getPath("")));
		basePaths.addAll(includePaths);
		return basePaths;
	}
	
	private Source parseInclude(Expression sourcePath, SourceFile baseSourceFile, boolean once) {
		for (Path includePath : getIncludePaths(baseSourceFile)) {
			Path fullPath = includePath.resolve(sourcePath.getString());
			if (Files.exists(fullPath)) {
				if (once && hasLoadedSourceFile(fullPath))
					return null;
				return parse(fullPath);
			}
		}
		throw new AssemblyException("Include file not found: " + sourcePath.getString());
	}
	
	public Source parse(Path sourcePath) {
		SourceFile sourceFile = new SourceFile(sourcePath);
		sourceFiles.add(sourceFile);
		return parse(sourceFile);
	}

	public Source parse(SourceFile sourceFile) {
		return parse(new Parser(sourceFile));
	}
	
	public Source parse(Parser parser) {
		while (true) {
			Line line = parser.parse(source.getScope());
			
			try {
				Directive directive = getDirective(line, parser);
				line.setDirective(directive);
				source.addLine(line);
				if (directive instanceof Terminator)
					return source;
			} catch (AssemblyException e) {
				e.addContext(line.getSourceSpan());
				throw e;
			}
		}
	}
	
	public Directive getDirective(Line line, Parser parser) {
		switch (line.getMnemonic()) {
		case "equ":
		case "EQU":
			return new Equ();
		case "include":
		case "INCLUDE":
			return getIncludeDirective(line, parser.getSourceFile());
		case "incbin":
		case "INCBIN":
			return new Incbin(getIncludePaths(parser.getSourceFile()));
		case "macro":
		case "MACRO":
			return new Macro(parseBlock(line.getScope(), ENDM_TERMINATORS, parser));
		case "rept":
		case "REPT":
			return new Rept(parseBlock(line.getScope(), ENDM_TERMINATORS, parser));
		case "irp":
		case "IRP":
			return new Irp(parseBlock(line.getScope(), ENDM_TERMINATORS, parser));
		case "proc":
		case "PROC":
			return new Proc(parseBlock(line.getScope(), ENDP_TERMINATORS, parser));
		case "if":
		case "IF":
			Source thenBlock = parseBlock(source.getScope(), ELSE_TERMINATORS, parser);
			Source elseBlock = !ENDIF_TERMINATORS.contains(thenBlock.getLastLine().getMnemonic()) ?
					parseBlock(source.getScope(), ENDIF_TERMINATORS, parser) : new Source(source.getScope());
			return new If(thenBlock, elseBlock);
		case "section":
		case "SECTION":
			return new Section(parseBlock(source.getScope(), ENDS_TERMINATORS, parser));
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
	
	private Directive getIncludeDirective(Line line, SourceFile sourceFile) {
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
		SourceBuilder sourceBuilder = new SourceBuilder(source.getScope(), END_TERMINATORS, includePaths);
		sourceBuilder.parseInclude(argument, sourceFile, once);
		return new Include(sourceBuilder.source);
	}
	
	private Source parseBlock(Scope scope, List<String> terminators, Parser parser) {
		return new SourceBuilder(scope, terminators, includePaths).parse(parser);
	}
	
}
