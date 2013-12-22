package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rla extends Instruction {
	
	public Rla(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "rla";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x17 };
	}

}
