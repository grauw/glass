package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Jp_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_HL_IX_IY);
	public static Schema ARGUMENTS_ALT = new Schema(Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Jp_HL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return indexifyDirect(argument.getRegister(), (byte)0xE9);
	}
	
}
