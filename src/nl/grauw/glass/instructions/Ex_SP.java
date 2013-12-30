package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ex_SP extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_SP, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Ex_SP(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return indexifyDirect(argument.getRegister(), (byte)0xE3);
	}
	
}
