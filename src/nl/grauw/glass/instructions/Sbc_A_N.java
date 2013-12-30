package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Sbc_A_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);
	
	private Expression argument;
	
	public Sbc_A_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xDE, (byte)argument.getInteger() };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("sbc", this);
			scope.addInstruction("SBC", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Sbc_A_N.ARGUMENTS.check(arguments))
				return new Sbc_A_N(arguments.getElement(1));
			return null;
		}
		
	}
	
}
