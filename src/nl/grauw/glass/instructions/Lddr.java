package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Lddr extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("lddr", this);
		scope.addInstruction("LDDR", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Lddr_.ARGUMENTS.check(arguments))
			return new Lddr_();
		throw new ArgumentException();
	}
	
	public static class Lddr_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xB8 };
		}
		
	}
	
}
