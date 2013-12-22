package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Adc extends Instruction {
	
	public Adc(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "adc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
