package nl.grauw.asm.instructions;

public class ArgumentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ArgumentException() {
		this("Invalid arguments.");
	}
	
	public ArgumentException(String message) {
		super(message);
	}
	
}
