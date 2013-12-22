package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rrca extends Instruction {
	
	public Rrca(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "rrca";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x0F };
	}

}
