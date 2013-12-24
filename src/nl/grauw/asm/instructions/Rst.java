package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Rst extends Instruction {
	
	private Expression argument;
	
	public Rst(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		int value = argument.getInteger();
		if (value < 0 || value > 0x38 || (value & 7) != 0)
			throw new ArgumentException();
		return new byte[] { (byte)(0xC7 + value) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "rst";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Rst(arguments.getElement(0));
			return null;
		}
		
	}
	
}
