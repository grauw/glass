package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Im extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("im", this);
		scope.addInstruction("IM", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Im_N.ARGUMENTS.check(arguments))
			return new Im_N(arguments.getElement(0));
		return null;
	}
	
	public static class Im_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Im_N(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int value = argument.getInteger();
			if (value == 0) {
				return new byte[] { (byte)0xED, (byte)0x46 };
			} else if (value == 1) {
				return new byte[] { (byte)0xED, (byte)0x56 };
			} else if (value == 2) {
				return new byte[] { (byte)0xED, (byte)0x5E };
			}
			throw new ArgumentException();
		}
		
	}
	
}
