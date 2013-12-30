package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rld extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("rld", this);
		scope.addInstruction("RLD", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rld_.ARGUMENTS.check(arguments))
			return new Rld_();
		throw new ArgumentException();
	}
	
	public static class Rld_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0x6F };
		}
		
	}
	
}
