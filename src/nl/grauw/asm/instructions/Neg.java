package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Neg extends Instruction {
	
	public Neg(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "neg";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x44 };
	}

}
