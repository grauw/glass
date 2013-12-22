package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rlc extends Instruction {
	
	public Rlc(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rlc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
