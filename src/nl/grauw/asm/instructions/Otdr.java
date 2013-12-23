package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Otdr extends Instruction {
	
	public Otdr(Expression arguments) {
		if (!ARGUMENTS_NONE.check(arguments))
			throw new ArgumentException("Too many arguments.");
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)0xBB };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "otdr";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Otdr(arguments);
		}
		
	}
	
}
