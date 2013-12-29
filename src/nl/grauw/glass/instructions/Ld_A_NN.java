package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_A_NN extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Ld_A_NN(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument.getAddress();
		return new byte[] { (byte)0x32, (byte)address, (byte)(address >> 8) };
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
				return new Ld_A_NN(arguments.getElement(0));
			return null;
		}
		
	}
	
}
