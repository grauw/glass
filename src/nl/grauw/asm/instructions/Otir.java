package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Otir extends Instruction {
	
	public Otir(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xB3 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "otir";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Otir(arguments);
		}
		
	}
	
}
