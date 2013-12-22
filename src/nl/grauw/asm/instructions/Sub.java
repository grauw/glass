package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Sub extends Instruction {
	
	public Sub(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "sub";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
