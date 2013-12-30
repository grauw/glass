package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Schema;

public class Ex_DE_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xEB };
	}
	
}
