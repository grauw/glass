package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Xor extends Arithmetic8Bit {
	
	public Xor(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00101000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "xor";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments) || ARGUMENTS_R.check(arguments))
				return new Xor(arguments);
			return null;
		}
		
	}
	
}
