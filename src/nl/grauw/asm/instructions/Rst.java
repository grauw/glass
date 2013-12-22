package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rst extends Instruction {
	
	public Rst(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rst";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
