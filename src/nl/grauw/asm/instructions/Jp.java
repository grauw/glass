package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Jp extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
	
	private Expression argument;
	
	public Jp(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xC3, (byte)argument.getInteger(), (byte)(argument.getInteger() >> 8) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "jp";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Jp(arguments.getElement(0));
			return null;
		}
		
	}
	
}
