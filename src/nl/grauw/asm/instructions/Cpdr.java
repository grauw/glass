package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Cpdr extends Instruction {
	
	public Cpdr(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "cpdr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB9 };
	}

}