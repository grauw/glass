package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Adc_A_N extends Instruction {
	
	private Expression argument;
	
	public Adc_A_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xCE, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "adc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_N.check(arguments))
				return new Adc_A_N(arguments.getElement(1));
			return null;
		}
		
	}
	
}
