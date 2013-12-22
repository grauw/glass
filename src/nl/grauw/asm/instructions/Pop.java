package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Pop extends Instruction {
	
	public Pop(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "pop";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
