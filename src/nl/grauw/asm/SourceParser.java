package nl.grauw.asm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

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
					if (line.getStatement().getArguments().size() != 1)
						throw new RuntimeException("Include only accepts 1 argument.");
					String argument = line.getStatement().getArguments().get(0);
					String includeFile = argument.substring(1, argument.length() - 1);
					parse(new File(includeFile));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return source;
	}
	
}
