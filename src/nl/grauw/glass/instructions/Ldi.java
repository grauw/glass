package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ldi extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("ldi", this);
		scope.addInstruction("LDI", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Ldi_.ARGUMENTS.check(arguments))
			return new Ldi_();
		throw new ArgumentException();
	}
	
	public static class Ldi_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA0 };
		}
		
	}
	
}
