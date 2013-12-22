package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Res extends Instruction {
	
	public Res(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "res";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
