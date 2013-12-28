package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Im extends Instruction {
	
	private Expression argument;
	
	public Im(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int value = argument.getInteger();
		if (value == 0) {
			return new byte[] { (byte)0xED, (byte)0x46 };
		} else if (value == 1) {
			return new byte[] { (byte)0xED, (byte)0x56 };
		} else if (value == 2) {
			return new byte[] { (byte)0xED, (byte)0x5E };
		}
		throw new ArgumentException();
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("im", this);
			registry.add("IM", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Im(arguments.getElement(0));
			return null;
		}
		
	}
	
}
