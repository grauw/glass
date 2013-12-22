package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Push extends Instruction {
	
	public Push(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "push";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
