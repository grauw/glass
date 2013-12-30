package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Retn extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("retn", this);
		scope.addInstruction("RETN", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Retn_.ARGUMENTS.check(arguments))
			return new Retn_();
		throw new ArgumentException();
	}
	
	public static class Retn_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)0x45 };
		}
		
	}
	
}
