package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Di extends Instruction {
	
	public Di(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "di";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xF3 };
	}

}
