package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Scf extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Scf_.ARGUMENTS.check(arguments))
			return new Scf_();
		throw new ArgumentException();
	}
	
	public static class Scf_ extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x37 };
		}
		
	}
	
}
