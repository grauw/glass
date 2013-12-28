package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Indr extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xED, (byte)0xBA };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("indr", this);
			registry.add("INDR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Indr();
			return null;
		}
		
	}
	
}
