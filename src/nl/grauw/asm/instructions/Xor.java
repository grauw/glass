package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Xor extends Arithmetic8Bit {
	
	public Xor(Expression arguments) {
		super(arguments, InstructionMask.XOR);
	}
	
	@Override
	public String getName() {
		return "xor";
	}
	
}
