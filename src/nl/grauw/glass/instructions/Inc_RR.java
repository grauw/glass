package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Inc_RR extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX);
	
	private Expression argument;
	
	public Inc_RR(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		Register register = argument.getRegister();
		return indexifyDirect(register, (byte)(0x03 | register.get16BitCode() << 4));
	}
	
}
