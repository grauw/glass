package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Indr extends Instruction {
	
	public Indr(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "indr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xBA };
	}

}