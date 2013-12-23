package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cp extends Arithmetic8Bit {
	
	public Cp(Expression arguments) {
		super(arguments, InstructionMask.CP);
	}
	
	@Override
	public String getName() {
		return "cp";
	}
	
}
