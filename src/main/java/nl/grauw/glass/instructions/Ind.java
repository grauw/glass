package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ind extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ind_.ARGUMENTS.check(arguments))
			return new Ind_();
		throw new ArgumentException();
	}
	
	public static class Ind_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xAA };
		}
		
	}
	
}
