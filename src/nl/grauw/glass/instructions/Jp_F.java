package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Jp_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Jp_F(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument2.getAddress();
		return new byte[] { (byte)(0xC2 | argument1.getFlag().getCode() << 3), (byte)address, (byte)(address >> 8) };
	}
	
}
