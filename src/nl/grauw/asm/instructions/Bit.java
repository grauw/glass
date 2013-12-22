package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Bit extends Instruction {
	
	public Bit(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "bit";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
