package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Nop extends Instruction {
	
	public Nop(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "nop";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
