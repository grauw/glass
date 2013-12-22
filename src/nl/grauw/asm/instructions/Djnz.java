package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Djnz extends Instruction {
	
	public Djnz(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "djnz";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
