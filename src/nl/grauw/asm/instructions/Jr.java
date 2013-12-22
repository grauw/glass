package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Jr extends Instruction {
	
	public Jr(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "jr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
