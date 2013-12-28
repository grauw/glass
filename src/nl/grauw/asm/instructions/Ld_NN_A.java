package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Ld_NN_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
	
	private Expression argument;
	
	public Ld_NN_A(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 3;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int address = argument.getAddress();
		return new byte[] { (byte)0x3A, (byte)address, (byte)(address >> 8) };
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
				return new Ld_NN_A(arguments.getElement(1));
			return null;
		}
		
	}
	
}
