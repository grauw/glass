package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Context;

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
