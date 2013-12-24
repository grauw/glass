package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class AddAN extends Arithmetic8Bit {
	
	public AddAN(Expression arguments) {
		super(arguments);
	}
	
	@Override
	protected int getMask() {
		return 0b00000000;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "add";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_N.check(arguments))
				return new AddAN(arguments.getElement(1));
			return null;
		}
		
	}
	
}
