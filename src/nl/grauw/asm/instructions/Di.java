package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Di extends Instruction {
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xF3 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "di";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Di();
			return null;
		}
		
	}
	
}
