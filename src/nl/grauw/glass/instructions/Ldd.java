package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ldd extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ldd_.ARGUMENTS.check(arguments))
			return new Ldd_();
		throw new ArgumentException();
	}
	
	public static class Ldd_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA8 };
		}
		
	}
	
}
