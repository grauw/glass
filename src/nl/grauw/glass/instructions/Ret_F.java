package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ret_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlag());
	
	private Expression argument;
	
	public Ret_F(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)(0xC0 | argument.getFlag().getCode() << 3) };
	}
	
}
