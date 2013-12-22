package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ei extends Instruction {
	
	public Ei(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ei";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xFB };
	}

}
