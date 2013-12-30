package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Reti extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("reti", this);
		scope.addInstruction("RETI", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Reti_.ARGUMENTS.check(arguments))
			return new Reti_();
		return null;
	}
	
	public static class Reti_ extends Instruction {
		
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
