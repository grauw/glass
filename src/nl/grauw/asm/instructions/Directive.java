package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;

public class Directive extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 0;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] {};
	}
	
}
