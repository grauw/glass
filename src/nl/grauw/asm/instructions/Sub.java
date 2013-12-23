package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Sub extends Arithmetic8Bit {
	
	public Sub(Expression arguments) {
		super(arguments, InstructionMask.SUB);
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "sub";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new Sub(arguments);
		}
		
	}
	
}
