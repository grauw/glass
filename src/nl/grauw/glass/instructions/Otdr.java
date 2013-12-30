package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Otdr extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Otdr_.ARGUMENTS.check(arguments))
			return new Otdr_();
		throw new ArgumentException();
	}
	
	public static class Otdr_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xBB };
		}
		
	}
	
}
