package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class AdcA extends Instruction {
	
	private Expression argument;
	
	public AdcA(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes() {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0x88 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "adc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_R.check(arguments))
				return new AdcA(arguments.getElement(1));
			return null;
		}
		
	}
	
}
