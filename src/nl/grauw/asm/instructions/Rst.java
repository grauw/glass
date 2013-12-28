package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Rst extends Instruction {
	
	private Expression argument;
	
	public Rst(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int value = argument.getInteger();
		if (value < 0 || value > 0x38 || (value & 7) != 0)
			throw new ArgumentException();
		return new byte[] { (byte)(0xC7 + value) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("rst", this);
			registry.add("RST", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Rst(arguments.getElement(0));
			return null;
		}
		
	}
	
}
