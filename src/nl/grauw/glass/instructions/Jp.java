package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Jp extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
	
	private Expression argument;
	
	public Jp(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument.getAddress();
		return new byte[] { (byte)0xC3, (byte)address, (byte)(address >> 8) };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("jp", this);
			scope.addInstruction("JP", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Jp_F.ARGUMENTS.check(arguments))
				return new Jp_F(arguments.getElement(0), arguments.getElement(1));
			if (Jp_HL.ARGUMENTS.check(arguments) || Jp_HL.ARGUMENTS_ALT.check(arguments))
				return new Jp_HL(arguments.getElement(0));
			if (Jp.ARGUMENTS.check(arguments))
				return new Jp(arguments.getElement(0));
			return null;
		}
		
	}
	
}
