package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Muluw extends Instruction {
	
	public Muluw(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "muluw";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
