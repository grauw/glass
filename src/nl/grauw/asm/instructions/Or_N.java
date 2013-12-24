package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Or_N extends Instruction {
	
	private Expression argument;
	
	public Or_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xF6, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "or";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Or_N(arguments);
			return null;
		}
		
	}
	
}