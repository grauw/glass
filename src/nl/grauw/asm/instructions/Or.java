package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Or extends Arithmetic8Bit {
	
	public Or(Expression arguments) {
		super(arguments, InstructionMask.OR);
	}
	
	@Override
	public String getName() {
		return "or";
	}
	
}
