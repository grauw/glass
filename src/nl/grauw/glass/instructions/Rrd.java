package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rrd extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rrd_.ARGUMENTS.check(arguments))
			return new Rrd_();
		throw new ArgumentException();
	}
	
	public static class Rrd_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0x67 };
		}
		
	}
	
}
