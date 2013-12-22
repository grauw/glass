package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Inc extends Instruction {
	
	public Inc(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "inc";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
