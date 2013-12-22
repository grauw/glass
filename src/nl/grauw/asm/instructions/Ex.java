package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ex extends Instruction {
	
	public Ex(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ex";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
