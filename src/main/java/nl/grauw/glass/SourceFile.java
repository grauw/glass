package nl.grauw.glass;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class SourceFile {

	private final Path path;
	private final List<String> content;

	public SourceFile(Path path) {
		this.path = path;
		try {
			this.content = Files.readAllLines(path, Charset.forName("ISO-8859-1"));
		} catch (IOException e) {
			throw new AssemblyException(e);
		}
	}

	public SourceFile(String string) {
		this.path = null;
		this.content = Arrays.asList(string.split("\\R", -1));
	}

	public SourceFileReader getReader() {
		return new SourceFileReader();
	}

	public Path getPath() {
		return path;
	}

	public class SourceFileReader {

		int lineNumber = 0;

		private SourceFileReader() {
		}

		public SourceFile getSourceFile() {
			return SourceFile.this;
		}

		public String readLine() {
			if (lineNumber >= content.size())
				return null;
			return content.get(lineNumber++);
		}

		public int getLineNumber() {
			return lineNumber;
		}

	}
}
