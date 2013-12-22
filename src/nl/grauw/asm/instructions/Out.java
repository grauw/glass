package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Out extends Instruction {
	
	public Out(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "out";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
