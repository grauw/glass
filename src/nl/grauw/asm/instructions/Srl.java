package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Srl extends Instruction {
	
	public Srl(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "srl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
