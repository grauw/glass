package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Djnz extends Instruction {
	
	private Expression argument;
	
	public Djnz(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int offset = argument.getInteger() - (context.getAddress() + 2);
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Jump offset out of range: " + offset);
		return new byte[] { (byte)0x10, (byte)offset };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "djnz";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Djnz(arguments);
			return null;
		}
		
	}
	
}
