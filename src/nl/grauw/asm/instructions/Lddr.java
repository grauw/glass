package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Lddr extends Instruction {
	
	public Lddr(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "lddr";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB8 };
	}

}
