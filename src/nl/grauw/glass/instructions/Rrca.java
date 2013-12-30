package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rrca extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("rrca", this);
		scope.addInstruction("RRCA", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rrca_.ARGUMENTS.check(arguments))
			return new Rrca_();
		throw new ArgumentException();
	}
	
	public static class Rrca_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x0F };
		}
		
	}
	
}
