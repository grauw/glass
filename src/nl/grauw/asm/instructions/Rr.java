package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rr extends Instruction {
	
	public Rr(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
