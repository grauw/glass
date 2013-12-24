package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Cp extends Arithmetic8Bit {
	
	public Cp(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00111000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "cp";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Cp(arguments);
			return null;
		}
		
	}
	
}
