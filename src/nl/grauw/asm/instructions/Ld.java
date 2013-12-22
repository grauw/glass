package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ld extends Instruction {
	
	public Ld(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ld";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
