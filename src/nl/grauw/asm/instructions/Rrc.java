package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rrc extends Instruction {
	
	public Rrc(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rrc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
