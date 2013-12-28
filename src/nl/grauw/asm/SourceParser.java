package nl.grauw.asm;

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
import java.util.List;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.expressions.StringLiteral;
import nl.grauw.asm.instructions.Macro;

public class SourceParser {
	
	private final LineParser lineParser = new LineParser();
	private final List<File> includePaths = new ArrayList<File>();
	private final Source source = new Source();
	
	String endDirective;
	
	public SourceParser(List<File> includePaths) {
		this.includePaths.add(null);
		this.includePaths.addAll(includePaths);
	}
	
	public String getEndDirective() {
		return endDirective;
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
		return parse(new InputStreamReader(reader, Charset.forName("US-ASCII")), sourceFile);
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
					processDirective(line, reader, sourceFile);
					if (endDirective != null)
						return source;
					source.addLine(line);
				} catch (AssemblyException e) {
					e.setContext(sourceFile, reader.getLineNumber(), lineText);
					throw e;
				}
			}
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
			if (line.getArguments() instanceof Sequence)
				throw new AssemblyException("Include only accepts 1 argument.");
			Expression argument = line.getArguments();
			if (!(argument instanceof StringLiteral))
				throw new AssemblyException("A string literal is expected.");
			String includeFile = ((StringLiteral)argument).getString();
			parseInclude(new File(includeFile));
			break;
		case "equ":
		case "EQU":
			if (line.getLabel() == null)
				throw new AssemblyException("Equ statement without label.");
			source.getScope().redefineLabel(line.getLabel().getName(), line.getArguments());
			break;
		case "macro":
		case "MACRO":
			if (line.getLabel() == null)
				throw new AssemblyException("Macro without label.");
			SourceParser parser = new SourceParser(includePaths);
			Source macroSource = parser.parse(reader, sourceFile);
			if (!"endm".equals(parser.getEndDirective()))
				throw new AssemblyException("Unexpected end directive: " + parser.getEndDirective());
			Macro.Factory factory = new Macro.Factory(line.getLabel().getName(), line.getArguments(), macroSource);
			factory.register(source.getInstructionFactory());
			break;
		case "endm":
		case "ENDM":
			endDirective = "endm";
			return;
		}
	}
	
}
