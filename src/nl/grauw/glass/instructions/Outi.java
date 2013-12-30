package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Outi extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("outi", this);
		scope.addInstruction("OUTI", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Outi_.ARGUMENTS.check(arguments))
			return new Outi_();
		return null;
	}
	
	public static class Outi_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA3 };
		}
		
	}
	
}
