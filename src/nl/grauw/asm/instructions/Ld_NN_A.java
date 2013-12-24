package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_NN_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
	
	private Expression argument;
	
	public Ld_NN_A(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0x3A, (byte)argument.getInteger(), (byte)(argument.getInteger() >> 8) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_NN_A(arguments.getElement(1));
			return null;
		}
		
	}
	
}
