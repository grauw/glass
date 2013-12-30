package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Out_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Out_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xD3, (byte)argument.getInteger() };
	}
	
}
