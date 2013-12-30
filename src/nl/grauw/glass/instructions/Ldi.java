package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ldi extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ldi_.ARGUMENTS.check(arguments))
			return new Ldi_();
		throw new ArgumentException();
	}
	
	public static class Ldi_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA0 };
		}
		
	}
	
}
