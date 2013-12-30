package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Nop extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("nop", this);
		scope.addInstruction("NOP", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Nop_.ARGUMENTS.check(arguments))
			return new Nop_();
		throw new ArgumentException();
	}
	
	public static class Nop_ extends Instruction {
		
		public static Schema ARGUMENTS = new Schema();
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x00 };
		}
		
	}
	
}
