package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Jr extends Instruction {
	
	public Jr(Expression arguments) {
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0x00 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "jr";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Jr(arguments);
		}
		
	}
	
}
