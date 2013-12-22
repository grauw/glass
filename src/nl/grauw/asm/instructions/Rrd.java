package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rrd extends Instruction {
	
	public Rrd(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public String getName() {
		return "rrd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x67 };
	}

}
