package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rra extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Rra_.ARGUMENTS.check(arguments))
			return new Rra_();
		throw new ArgumentException();
	}
	
	public static class Rra_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x1F };
		}
		
	}
	
}
