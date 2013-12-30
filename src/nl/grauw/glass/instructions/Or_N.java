package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Or_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
	
	private Expression argument;
	
	public Or_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xF6, (byte)argument.getInteger() };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("or", this);
			scope.addInstruction("OR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Or_N(arguments);
			return null;
		}
		
	}
	
}
