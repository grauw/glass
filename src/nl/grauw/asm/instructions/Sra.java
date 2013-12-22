package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Sra extends Instruction {
	
	public Sra(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "sra";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
