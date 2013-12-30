package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ccf extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ccf_.ARGUMENTS.check(arguments))
			return new Ccf_();
		throw new ArgumentException();
	}
	
	public static class Ccf_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x3F };
		}
		
	}
	
}
