package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Sub_N extends Instruction {
	
	private Expression argument;
	
	public Sub_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xD6, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "sub";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Sub_N(arguments);
			return null;
		}
		
	}
	
}
