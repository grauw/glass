package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Exx extends Instruction {
	
	public Exx(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "exx";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xD9 };
	}

}
