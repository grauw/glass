package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;

public class Retn extends Instruction {
	
	public Retn(Expression arguments) {
	}
	
	@Override
	public String getName() {
		return "retn";
	}

	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x45 };
	}

}
