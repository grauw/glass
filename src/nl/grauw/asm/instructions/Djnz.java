package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Djnz extends Instruction {
	
	public Djnz(Expression arguments) {
	}
	
	@Override
	public byte[] getBytes(Context line) {
		return new byte[] { (byte)0x00 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "djnz";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Djnz(arguments);
		}
		
	}
	
}
