package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Im extends Instruction {
	
	public Im(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "im";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
