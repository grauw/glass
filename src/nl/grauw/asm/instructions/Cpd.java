package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cpd extends Instruction {
	
	public Cpd(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "cpd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA9 };
	}

}
