package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ini extends Instruction {
	
	public Ini(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "ini";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA2 };
	}

}
