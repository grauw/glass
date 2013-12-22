package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Jp extends Instruction {
	
	public Jp(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "jp";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
