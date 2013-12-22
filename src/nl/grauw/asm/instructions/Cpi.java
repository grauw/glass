package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cpi extends Instruction {
	
	public Cpi(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "cpi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA1 };
	}

}
