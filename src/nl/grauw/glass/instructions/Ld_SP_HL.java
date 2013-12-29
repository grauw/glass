package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_SP_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_SP, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Ld_SP_HL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return indexifyDirect(argument.getRegister(), (byte)0xF9);
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ld", this);
			scope.addInstruction("LD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_SP_HL(arguments.getElement(1));
			return null;
		}
		
	}
	
}
