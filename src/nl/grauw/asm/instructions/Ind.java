package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ind extends Instruction {
	
	public Ind(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ind";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xAA };
	}

}
