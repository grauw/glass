package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Cpi extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xED, (byte)0xA1 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "cpi";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Cpi();
			return null;
		}
		
	}
	
}
