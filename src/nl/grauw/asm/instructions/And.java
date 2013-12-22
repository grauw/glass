package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class And extends Instruction {
	
	public And(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "and";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
