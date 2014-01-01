package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Di extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Di_.ARGUMENTS.check(arguments))
			return new Di_();
		throw new ArgumentException();
	}
	
	public static class Di_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xF3 };
		}
		
	}
	
}
