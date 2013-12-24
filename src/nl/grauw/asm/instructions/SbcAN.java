package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class SbcAN extends Arithmetic8Bit {
	
	public SbcAN(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00011000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "sbc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_N.check(arguments))
				return new SbcAN(arguments.getElement(1));
			return null;
		}
		
	}
	
}
