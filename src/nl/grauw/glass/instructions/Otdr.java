package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Otdr extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("otdr", this);
		scope.addInstruction("OTDR", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Otdr_.ARGUMENTS.check(arguments))
			return new Otdr_();
		return null;
	}
	
	public static class Otdr_ extends Instruction {
		
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
