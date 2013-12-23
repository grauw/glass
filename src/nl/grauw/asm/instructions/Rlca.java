package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rlca extends Instruction {
	
	public Rlca(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "rlca";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x07 };
	}

}