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

		public SourceFileSpan getSpan(SourceFileSpan span) {
			return new SourceFileSpan(span != null ? span.lineStart : lineNumber, lineNumber + 1);
		}
	}

	public class SourceFileSpan {

		final int lineStart;
		final int lineEnd;
		final int column;

		public SourceFileSpan(int lineStart, int lineEnd, int column) {
			this.lineStart = lineStart;
			this.lineEnd = lineEnd;
			this.column = column;
		}

		public SourceFileSpan(int lineStart, int lineEnd) {
			this(lineStart, lineEnd, -1);
		}

		public SourceFile getSourceFile() {
			return SourceFile.this;
		}

		public SourceFileSpan atColumn(int column) {
			return new SourceFileSpan(lineStart, lineEnd, column);
		}

		@Override
		public String toString() {
			String string = "[at " + getSourceFile().getPath() + ":" + (lineStart + 1) + (column != -1 ? "," + (column + 1) : "") + "]";

			for (int i = lineStart; i < lineEnd && i < content.size(); i++) {
				string += "\n" + content.get(i);
			}

			if (column != -1) {
				String line = content.get(Math.min(lineEnd, content.size()) - 1);
				int end = Math.min(column, line.length());
				string += "\n" + line.substring(0, end).replaceAll("[^\t]", " ") + "^";
			}

			return string;
		}
	}
}
