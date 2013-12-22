package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Xor extends Instruction {
	
	public Xor(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "xor";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
