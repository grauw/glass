package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ini extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("ini", this);
		scope.addInstruction("INI", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Ini_.ARGUMENTS.check(arguments))
			return new Ini_();
		return null;
	}
	
	public static class Ini_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0xA2 };
		}
		
	}
	
}
