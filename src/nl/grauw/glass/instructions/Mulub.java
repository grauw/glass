package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Mulub extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R);
	
	private Expression argument;
	
	public Mulub(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)(0xC1 | argument.getRegister().get8BitCode() << 3) };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("mulub", this);
			scope.addInstruction("MULUB", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Mulub.ARGUMENTS.check(arguments))
				return new Mulub(arguments.getElement(1));
			return null;
		}
		
	}
	
}
