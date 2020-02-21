package nl.grauw.glass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.grauw.glass.SourceFile.SourceFileSpan;

public class AssemblyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final List<SourceFileSpan> contexts = new ArrayList<>();

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

	public void addContext(SourceFileSpan sourceSpan) {
		contexts.add(sourceSpan);
	}

	@Override
	public String getMessage() {
		String message = super.getMessage();

		for (SourceFileSpan context : contexts)
			message += "\n" + context;

		return message;
	}

	public String getPlainMessage() {
		return super.getMessage();
	}

	public List<SourceFileSpan> getContexts() {
		return Collections.unmodifiableList(contexts);
	}

}
