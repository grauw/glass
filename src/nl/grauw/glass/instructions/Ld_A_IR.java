package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Ld_A_IR extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_IR);
	
	private Expression argument;
	
	public Ld_A_IR(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		if (argument.getRegister() == Register.I)
			return new byte[] { (byte)0xED, (byte)0x57 };
		return new byte[] { (byte)0xED, (byte)0x5F };
	}
	
}
