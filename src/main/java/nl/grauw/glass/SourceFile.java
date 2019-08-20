package nl.grauw.glass;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class SourceFile {

	private final Path path;
	private final String content;

	public SourceFile(Path path) {
		this.path = path;
		try {
			this.content = new String(Files.readAllBytes(path), Charset.forName("ISO-8859-1"));
		} catch (IOException e) {
			throw new AssemblyException(e);
		}
	}

	public SourceFile(String content) {
		this.path = null;
		this.content = content;
	}

	public SourceFileReader getReader() {
		return new SourceFileReader();
	}

	public Path getPath() {
		return path;
	}

	public class SourceFileReader extends LineNumberReader {

		private SourceFileReader() {
			super(new LineNumberReader(new StringReader(content)));
		}

		public SourceFile getSourceFile() {
			return SourceFile.this;
		}

	}
}
