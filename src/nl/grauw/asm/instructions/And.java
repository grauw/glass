package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class And extends Arithmetic8Bit {
	
	public And(Expression arguments) {
		super(arguments, InstructionMask.AND);
	}
	
	@Override
	public String getName() {
		return "and";
	}
	
}
