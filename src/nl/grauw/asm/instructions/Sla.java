package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Sla extends Instruction {
	
	public Sla(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "sla";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}

}
