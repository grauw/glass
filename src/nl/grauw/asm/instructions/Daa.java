package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Daa extends Instruction {
	
	public Daa(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "daa";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x27 };
	}

}
