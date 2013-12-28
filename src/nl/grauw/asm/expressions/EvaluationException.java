package nl.grauw.asm.expressions;

import nl.grauw.asm.AssemblyException;

public class EvaluationException extends AssemblyException {
	private static final long serialVersionUID = 1L;
	
	public EvaluationException() {
		this(null);
	}
	
	public EvaluationException(String message) {
		super(message);
	}
	
}
