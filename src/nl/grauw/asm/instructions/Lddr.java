package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Lddr extends Instruction {
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB8 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "lddr";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Lddr();
			return null;
		}
		
	}
	
}
