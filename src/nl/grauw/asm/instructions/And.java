package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class And extends Arithmetic8Bit {
	
	public And(Expression arguments) {
		super(arguments, InstructionMask.AND);
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "and";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new And(arguments);
		}
		
	}
	
}
