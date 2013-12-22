package nl.grauw.asm.instructions;

public abstract class Instruction {
	
	public abstract String getName();
	
	public abstract byte[] getBytes();
	
	public String toString() {
		return getName();
	}
	
}
