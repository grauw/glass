package nl.grauw.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.StringLiteral;

public class SourceParser {
	
	LineParser lineParser = new LineParser();
	Source source = new Source();
	
	public Source parse(File sourceFile) {
		System.out.println("Reading file: " + sourceFile);
		try {
			return parse(
				new LineNumberReader(new InputStreamReader(new FileInputStream(sourceFile),
					Charset.forName("US-ASCII"))), sourceFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Source parse(LineNumberReader reader, File sourceFile) {
		try {
			while (reader.ready()) {
				Line line = source.AddLine(lineParser.parse(reader.readLine(), sourceFile, reader.getLineNumber()));
				
				Statement statement = line.getStatement();
				if (statement != null && (statement.getInstruction().equals("INCLUDE") || statement.getInstruction().equals("include"))) {
					if (statement.getArguments().size() != 1)
						throw new RuntimeException("Include only accepts 1 argument.");
					Expression argument = statement.getArguments().get(0);
					if (!(argument instanceof StringLiteral))
						throw new RuntimeException("A string literal is expected.");
					String includeFile = ((StringLiteral)argument).getString();
					parse(new File(includeFile));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return source;
	}
	
}
