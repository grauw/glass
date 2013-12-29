package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Call extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
	
	private Expression argument;
	
	public Call(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument.getAddress();
		return new byte[] { (byte)0xCD, (byte)address, (byte)(address >> 8) };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("call", this);
			scope.addInstruction("CALL", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Call(arguments.getElement(0));
			return null;
		}
		
	}
	
}
