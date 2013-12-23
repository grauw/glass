package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class And extends Arithmetic8Bit {
	
	public And(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00100000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "and";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments) || ARGUMENTS_R.check(arguments))
				return new And(arguments);
			return null;
		}
		
	}
	
}
