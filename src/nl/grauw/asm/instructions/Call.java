package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Call extends Instruction {
	
	public Call(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "call";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
