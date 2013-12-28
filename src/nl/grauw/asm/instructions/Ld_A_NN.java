package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Ld_A_NN extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Ld_A_NN(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int address = argument.getAddress();
		return new byte[] { (byte)0x32, (byte)address, (byte)(address >> 8) };
	}
	
	public static class Factory implements InstructionFactory {
		
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
