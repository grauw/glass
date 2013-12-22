package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Dec extends Instruction {
	
	public Dec(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "dec";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
