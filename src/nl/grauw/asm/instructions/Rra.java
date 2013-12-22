package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Rra extends Instruction {
	
	public Rra(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "rra";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x1F };
	}

}
