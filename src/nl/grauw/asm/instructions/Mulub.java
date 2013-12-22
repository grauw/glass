package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Mulub extends Instruction {
	
	public Mulub(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "mulub";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
