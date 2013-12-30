package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Cpdr extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Cpdr_.ARGUMENTS.check(arguments))
			return new Cpdr_();
		throw new ArgumentException();
	}
	
	public static class Cpdr_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xB9 };
		}
		
	}
	
}
