package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class AdcA extends Arithmetic8Bit {
	
	public AdcA(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00001000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "adc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_N.check(arguments) || ARGUMENTS_A_R.check(arguments))
				return new AdcA(arguments.getElement(1));
			return null;
		}
		
	}
	
}
