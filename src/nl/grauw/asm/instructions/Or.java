package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Or extends Instruction {
	
	public Or(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "or";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
