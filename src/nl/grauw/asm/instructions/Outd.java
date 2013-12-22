package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Outd extends Instruction {
	
	public Outd(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "outd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xAB };
	}

}
