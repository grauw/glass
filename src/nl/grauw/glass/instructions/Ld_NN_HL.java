package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_NN_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_NN_HL(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument2.getRegister(), 3);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument1.getAddress();
		return indexifyDirect(argument2.getRegister(), (byte)0x22, (byte)address, (byte)(address >> 8));
	}
	
}
