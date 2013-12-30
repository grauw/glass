package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Neg extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("neg", this);
		scope.addInstruction("NEG", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Neg_.ARGUMENTS.check(arguments))
			return new Neg_();
		return null;
	}
	
	public static class Neg_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0x44 };
		}
		
	}
	
}
