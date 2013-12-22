package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class In extends Instruction {
	
	public In(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "in";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
