package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class SbcA extends Arithmetic8Bit {
	
	public SbcA(Expression arguments) {
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
			if (arguments instanceof Sequence && ((Sequence)arguments).getValue() == Register.A)
				return new SbcA(((Sequence)arguments).getTail());
			return null;
		}
		
	}
	
}
