package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Sbc extends Instruction {
	
	public Sbc(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "sbc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
