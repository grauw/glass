package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Neg extends Instruction {
	
	public Neg(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0x44 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "neg";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Neg(arguments);
		}
		
	}
	
}
