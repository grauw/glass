package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ldd extends Instruction {
	
	public Ldd(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ldd";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA8 };
	}

}
