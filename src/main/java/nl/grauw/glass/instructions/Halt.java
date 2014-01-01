package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Halt extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Halt_.ARGUMENTS.check(arguments))
			return new Halt_();
		throw new ArgumentException();
	}
	
	public static class Halt_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x76 };
		}
		
	}
	
}
