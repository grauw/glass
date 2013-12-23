package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Call extends Instruction {
	
	public Call(Expression arguments) {
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x00 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "call";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Call(arguments);
		}
		
	}
	
}
