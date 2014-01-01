package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Reti extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Reti_.ARGUMENTS.check(arguments))
			return new Reti_();
		throw new ArgumentException();
	}
	
	public static class Reti_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0x4D };
		}
		
	}
	
}
