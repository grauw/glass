package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Sub extends Arithmetic8Bit {
	
	public Sub(Expression arguments) {
		super(arguments, InstructionMask.SUB);
	}
	
	@Override
	public String getName() {
		return "sub";
	}
	
}
