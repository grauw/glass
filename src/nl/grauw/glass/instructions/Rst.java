package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rst extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rst_N.ARGUMENTS.check(arguments))
			return new Rst_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Rst_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Rst_N(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int value = argument.getInteger();
			if (value < 0 || value > 0x38 || (value & 7) != 0)
				throw new ArgumentException();
			return new byte[] { (byte)(0xC7 + value) };
		}
		
	}
	
}
