package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Set extends Instruction {
	
	public Set(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "set";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
