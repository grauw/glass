package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Scf extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("scf", this);
		scope.addInstruction("SCF", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Scf_.ARGUMENTS.check(arguments))
			return new Scf_();
		return null;
	}
	
	public static class Scf_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x37 };
		}
		
	}
	
}
