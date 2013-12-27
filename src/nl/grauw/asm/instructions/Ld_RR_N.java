package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_RR_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX, Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_RR_N(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(argument1.getRegister(), 3);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument1.getRegister();
		return indexifyDirect(register, (byte)(0x01 | register.get16BitCode() << 4),
				(byte)argument2.getInteger(), (byte)(argument2.getInteger() >> 8));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_RR_N(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
