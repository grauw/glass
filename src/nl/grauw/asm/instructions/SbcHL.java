package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class SbcHL extends Instruction {
	
	private Expression argument;
	
	public SbcHL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0x42 | argument.getRegister().get16BitCode() << 4) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "sbc";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_HL_RR.check(arguments))
				return new SbcHL(arguments.getElement(1));
			return null;
		}
		
	}
	
}
