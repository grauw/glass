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
	Source source = new Source();
	List<File> includePaths = new ArrayList<File>();
	
	public SourceParser(List<File> includePaths) {
		this.includePaths.add(null);
		this.includePaths.addAll(includePaths);
	}
	
	public Source parse(File sourceFile) {
		System.out.println("Reading file: " + sourceFile);
		try {
			parse(new FileInputStream(sourceFile), sourceFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
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
		throw new RuntimeException("Include file not found: " + sourceFile);
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
				Line line = source.addLine(lineParser.parse(lineText, sourceFile, reader.getLineNumber()));
				processDirective(line, reader, sourceFile);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return source;
	}
	
	public void processDirective(Line line, LineNumberReader reader, File sourceFile) {
		Statement statement = line.getStatement();
		if (statement == null)
			return;
		
		switch (statement.getMnemonic()) {
		case "include":
		case "INCLUDE":
			if (statement.getArguments() instanceof Sequence)
				throw new RuntimeException("Include only accepts 1 argument.");
			Expression argument = statement.getArguments();
			if (!(argument instanceof StringLiteral))
				throw new RuntimeException("A string literal is expected.");
			String includeFile = ((StringLiteral)argument).getString();
			parseInclude(new File(includeFile));
			break;
		case "equ":
		case "EQU":
			if (line.getLabel() == null)
				throw new RuntimeException("Equ statement without label.");
			source.getScope().addLabel(line.getLabel().getName(), statement.getArguments());
			break;
		}
	}
	
}
