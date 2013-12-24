package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class AdcHL extends Instruction {
	
	private Expression argument;
	
	public AdcHL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0x4A | argument.getRegister().get16BitCode() << 4) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "adc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_HL_RR.check(arguments))
				return new AdcHL(arguments.getElement(1));
			return null;
		}
		
	}
	
}
