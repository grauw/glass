package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Halt extends Instruction {
	
	public Halt(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "halt";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x76 };
	}

}
