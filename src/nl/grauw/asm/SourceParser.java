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

public class SourceParser {
	
	LineParser lineParser = new LineParser();
	List<File> includePaths = new ArrayList<File>();
	Source source = new Source();
	
	public SourceParser(List<File> includePaths) {
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
					source.addLine(lineParser.parse(lineText, line));
					processDirective(line, reader, sourceFile);
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
		}
	}
	
}
