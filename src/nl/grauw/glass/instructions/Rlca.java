package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rlca extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("rlca", this);
		scope.addInstruction("RLCA", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rlca_.ARGUMENTS.check(arguments))
			return new Rlca_();
		return null;
	}
	
	public static class Rlca_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x07 };
		}
		
	}
	
}
