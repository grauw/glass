package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Ld_IR_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_IR, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Ld_IR_A(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		if (argument.getRegister() == Register.I)
			return new byte[] { (byte)0xED, (byte)0x47 };
		return new byte[] { (byte)0xED, (byte)0x4F };
	}
	
}
