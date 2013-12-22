package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cpl extends Instruction {
	
	public Cpl(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "cpl";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x2F };
	}

}
