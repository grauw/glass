package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_NN_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_NN_HL(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(argument2.getRegister(), 3);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int address = argument1.getAddress();
		return indexifyDirect(argument2.getRegister(), (byte)0x22, (byte)address, (byte)(address >> 8));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_NN_HL(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
