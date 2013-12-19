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
				System.out.println(line.toString());
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return source;
	}
	
}
