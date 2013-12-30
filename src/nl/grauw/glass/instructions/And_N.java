package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class And_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
	
	private Expression argument;
	
	public And_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xE6, (byte)argument.getInteger() };
	}
	
}
