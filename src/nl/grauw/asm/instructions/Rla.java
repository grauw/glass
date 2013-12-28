package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Rla extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0x17 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("rla", this);
			registry.add("RLA", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Rla();
			return null;
		}
		
	}
	
}
