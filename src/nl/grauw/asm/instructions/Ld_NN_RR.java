package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_NN_RR extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_RR);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_NN_RR(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0x43 | argument2.getRegister().get16BitCode() << 4),
				(byte)argument1.getInteger(), (byte)(argument1.getInteger() >> 8) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_NN_RR(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
