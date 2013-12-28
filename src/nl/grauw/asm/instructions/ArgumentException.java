package nl.grauw.asm.instructions;

import nl.grauw.asm.AssemblyException;

public class ArgumentException extends AssemblyException {
	private static final long serialVersionUID = 1L;
	
	public ArgumentException() {
		this("Invalid arguments.");
	}
	
	public ArgumentException(String message) {
		super(message);
	}
	
}
