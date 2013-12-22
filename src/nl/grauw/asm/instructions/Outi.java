package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Outi extends Instruction {
	
	public Outi(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "outi";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xA3 };
	}

}
