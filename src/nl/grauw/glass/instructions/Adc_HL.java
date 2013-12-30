package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Adc_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);
	
	private Expression argument;
	
	public Adc_HL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)(0x4A | argument.getRegister().get16BitCode() << 4) };
	}
	
}
