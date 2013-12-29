package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Rst extends Instruction {
	
	private Expression argument;
	
	public Rst(Expression argument) {
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
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("rst", this);
			scope.addInstruction("RST", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Rst(arguments.getElement(0));
			return null;
		}
		
	}
	
}
