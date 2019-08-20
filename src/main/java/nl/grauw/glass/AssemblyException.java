package nl.grauw.glass;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AssemblyException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final List<Context> contexts = new ArrayList<>();
	
	public AssemblyException() {
		this((Throwable)null);
	}
	
	public AssemblyException(String message) {
		this(message, null);
	}
	
	public AssemblyException(Throwable cause) {
		this("Error during assembly.", null);
	}
	
	public AssemblyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public void addContext(Line line) {
		addContext(line.getSourcePath(), line.getLineNumber(), -1, line.getSourceText());
	}
	
	public void addContext(Path path, int line, int column, String text) {
		contexts.add(new Context(path, line, column, text));
	}
	
	@Override
	public String getMessage() {
		String message = super.getMessage();
		
		for (Context context : contexts)
			message += "\n" + context;
		
		return message;
	}
	
	public String getPlainMessage() {
		return super.getMessage();
	}
	
	private static class Context {
		
		private final Path path;
		private final int line;
		private final int column;
		private final String text;
		
		public Context(Path path, int line, int column, String text) {
			this.path = path;
			this.line = line;
			this.column = column;
			this.text = text;
		}
		
		@Override
		public String toString() {
			String prefix = "[at " + path + ":" + line + (column != -1 ? "," + column : "") + "]\n";
			String context = prefix + text;
			
			if (column >= 0) {
				int start = Math.min(context.lastIndexOf('\n') + 1, context.length());
				int end = Math.min(start + column, context.length());
				context += "\n" + context.substring(start, end).replaceAll("[^\t]", " ") + "^";
			}
			
			return context;
		}
		
	}
	
}
