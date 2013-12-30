package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Cpl extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("cpl", this);
		scope.addInstruction("CPL", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Cpl_.ARGUMENTS.check(arguments))
			return new Cpl_();
		throw new ArgumentException();
	}
	
	public static class Cpl_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x2F };
		}
		
	}
	
}
