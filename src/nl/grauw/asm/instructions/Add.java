package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Add extends Instruction {
	
	public Add(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "add";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
