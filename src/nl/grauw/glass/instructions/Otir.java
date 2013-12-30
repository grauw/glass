package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Otir extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Otir_.ARGUMENTS.check(arguments))
			return new Otir_();
		throw new ArgumentException();
	}
	
	public static class Otir_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xB3 };
		}
		
	}
	
}
