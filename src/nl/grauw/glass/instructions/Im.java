package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Im extends Instruction {
	
	private Expression argument;
	
	public Im(Expression argument) {
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
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("im", this);
			scope.addInstruction("IM", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Im(arguments.getElement(0));
			return null;
		}
		
	}
	
}
