package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rld extends Instruction {
	
	public Rld(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rld";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x6F };
	}

}
