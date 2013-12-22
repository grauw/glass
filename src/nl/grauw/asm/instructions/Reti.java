package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Reti extends Instruction {
	
	public Reti(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "reti";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x4D };
	}

}
