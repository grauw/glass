package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ldir extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("ldir", this);
		scope.addInstruction("LDIR", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Ldir_.ARGUMENTS.check(arguments))
			return new Ldir_();
		return null;
	}
	
	public static class Ldir_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xB0 };
		}
		
	}
	
}
