package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ldi extends Instruction {
	
	public Ldi(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "ldi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA0 };
	}

}
