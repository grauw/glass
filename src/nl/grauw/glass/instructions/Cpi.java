package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Cpi extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("cpi", this);
		scope.addInstruction("CPI", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Cpi_.ARGUMENTS.check(arguments))
			return new Cpi_();
		throw new ArgumentException();
	}
	
	public static class Cpi_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA1 };
		}
		
	}
	
}
