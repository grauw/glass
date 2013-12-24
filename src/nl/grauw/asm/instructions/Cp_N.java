package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Cp_N extends Instruction {
	
	private Expression argument;
	
	public Cp_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xFE, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "cp";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Cp_N(arguments);
			return null;
		}
		
	}
	
}
