package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rla extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("rla", this);
		scope.addInstruction("RLA", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rla_.ARGUMENTS.check(arguments))
			return new Rla_();
		throw new ArgumentException();
	}
	
	public static class Rla_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x17 };
		}
		
	}
	
}
