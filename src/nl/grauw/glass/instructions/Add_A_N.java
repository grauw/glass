package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Add_A_N extends Instruction {
	
	private Expression argument;
	
	public Add_A_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xC6, (byte)argument.getInteger() };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("add", this);
			scope.addInstruction("ADD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_N.check(arguments))
				return new Add_A_N(arguments.getElement(1));
			return null;
		}
		
	}
	
}
