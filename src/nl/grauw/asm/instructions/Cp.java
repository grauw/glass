package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cp extends Instruction {
	
	public Cp(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "cp";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
