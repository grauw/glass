package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ldir extends Instruction {
	
	public Ldir(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ldir";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB0 };
	}

}
