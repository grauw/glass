package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Inc extends Instruction {
	
	public Inc(Expression arguments) {
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "inc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Inc(arguments);
		}
		
	}
	
}
