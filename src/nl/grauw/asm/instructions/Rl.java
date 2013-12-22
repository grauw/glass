package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rl extends Instruction {
	
	public Rl(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
