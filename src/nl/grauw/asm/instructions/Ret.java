package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Ret extends Instruction {
	
	public Ret(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "ret";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
