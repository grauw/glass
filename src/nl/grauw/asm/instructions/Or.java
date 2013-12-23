package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Or extends Arithmetic8Bit {
	
	public Or(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00110000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "or";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments) || ARGUMENTS_R.check(arguments))
				return new Or(arguments);
			return null;
		}
		
	}
	
}
