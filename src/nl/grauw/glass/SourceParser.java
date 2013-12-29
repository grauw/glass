package nl.grauw.glass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Sequence;
import nl.grauw.glass.expressions.StringLiteral;
import nl.grauw.glass.instructions.Macro;

public class SourceParser {
	
	public static final List<String> END_TERMINATORS = Arrays.asList(new String[] { "end", "END" });
	public static final List<String> ENDM_TERMINATORS = Arrays.asList(new String[] { "endm", "ENDM" });
	
	private final Source source;
	private final List<String> terminators;
	private final List<File> includePaths = new ArrayList<File>();
	private final LineParser lineParser = new LineParser();
	
	public SourceParser(List<File> includePaths) {
		this.source = new Source();
		this.terminators = END_TERMINATORS;
		this.includePaths.add(null);
		this.includePaths.addAll(includePaths);
	}
	
	public SourceParser(Scope parentScope, List<String> terminators, List<File> includePaths) {
		this.source = new Source(parentScope);
		this.terminators = terminators;
		this.includePaths.add(null);
		this.includePaths.addAll(includePaths);
	}
	
	public Source parse(File sourceFile) {
		System.out.println("Reading file: " + sourceFile);
		try {
			parse(new FileInputStream(sourceFile), sourceFile);
		} catch (FileNotFoundException e) {
			throw new AssemblyException(e);
		}
		return source;
	}
	
	private Source parseInclude(File sourceFile) {
		for (File includePath : includePaths) {
			File fullPath = new File(includePath, sourceFile.getPath());
			if (fullPath.exists()) {
				parse(fullPath);
				return source;
			}
		}
		throw new AssemblyException("Include file not found: " + sourceFile);
	}
	
	public Source parse(InputStream reader, File sourceFile) {
		return parse(new InputStreamReader(reader, Charset.forName("ISO-8859-1")), sourceFile);
	}
	
	public Source parse(Reader reader, File sourceFile) {
		return parse(new LineNumberReader(reader), sourceFile);
	}
	
	private Source parse(LineNumberReader reader, File sourceFile) {
		try {
			String lineText;
			while ((lineText = reader.readLine()) != null) {
				try {
					Line line = new Line(source.getScope(), sourceFile, reader.getLineNumber());
					lineParser.parse(lineText, line);
					if (line.getMnemonic() != null && terminators.contains(line.getMnemonic()))
						return source;
					processDirective(line, reader, sourceFile);
					source.addLine(line);
				} catch (AssemblyException e) {
					e.setContext(sourceFile, reader.getLineNumber(), lineText);
					throw e;
				}
			}
			if (terminators != END_TERMINATORS)
				throw new AssemblyException("Unexpected end of file. Expecting: " + terminators.toString());
		} catch (IOException e) {
			throw new AssemblyException(e);
		}
		return source;
	}
	
	public void processDirective(Line line, LineNumberReader reader, File sourceFile) {
		if (line.getMnemonic() == null)
			return;
		
		switch (line.getMnemonic()) {
		case "include":
		case "INCLUDE":
			processInclude(line);
			break;
		case "equ":
		case "EQU":
			processEqu(line);
			break;
		case "macro":
		case "MACRO":
			processMacro(line, reader, sourceFile);
			break;
		}
	}
	
	private void processInclude(Line line) {
		if (line.getArguments() instanceof Sequence)
			throw new AssemblyException("Include only accepts 1 argument.");
		Expression argument = line.getArguments();
		if (!(argument instanceof StringLiteral))
			throw new AssemblyException("A string literal is expected.");
		String includeFile = ((StringLiteral)argument).getString();
		parseInclude(new File(includeFile));
	}
	
	private void processEqu(Line line) {
		if (line.getLabel() == null)
			throw new AssemblyException("Equ statement without label.");
		source.getScope().redefineLabel(line.getLabel().getName(), line.getArguments());
	}
	
	private void processMacro(Line line, LineNumberReader reader, File sourceFile) {
		if (line.getLabel() == null)
			throw new AssemblyException("Macro without label.");
		SourceParser parser = new SourceParser(source.getScope(), ENDM_TERMINATORS, includePaths);
		Source macroSource = parser.parse(reader, sourceFile);
		Macro.Factory factory = new Macro.Factory(line.getLabel().getName(), line.getArguments(), macroSource);
		factory.register(source.getScope());
	}
	
}