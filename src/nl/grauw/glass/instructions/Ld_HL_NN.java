package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_HL_NN extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL_IX_IY, Schema.INDIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_HL_NN(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(argument1.getRegister(), 3);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int address = argument2.getAddress();
		return indexifyDirect(argument1.getRegister(), (byte)0x2A, (byte)address, (byte)(address >> 8));
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
				return new Ld_HL_NN(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}