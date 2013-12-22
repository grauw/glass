package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ccf extends Instruction {
	
	public Ccf(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ccf";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x3F };
	}

}
