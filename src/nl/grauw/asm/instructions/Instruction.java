package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.ExpressionSchema;

public abstract class Instruction {
	
	public static ExpressionSchema ARGUMENTS_NONE = ExpressionSchema.NONE;
	
	public abstract String getName();
	
	public abstract byte[] getBytes();
	
	public String toString() {
		return getName();
	}
	
}
