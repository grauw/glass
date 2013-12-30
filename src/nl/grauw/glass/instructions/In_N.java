package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class In_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
	
	private Expression argument;
	
	public In_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xDB, (byte)argument.getInteger() };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("in", this);
			scope.addInstruction("IN", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (In_N.ARGUMENTS.check(arguments))
				return new In_N(arguments.getElement(1));
			return null;
		}
		
	}
	
}
