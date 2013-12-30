package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_RR_NN extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR, Schema.INDIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_RR_NN(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return 4;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument2.getAddress();
		return new byte[] { (byte)0xED, (byte)(0x4B | argument1.getRegister().get16BitCode() << 4),
				(byte)address, (byte)(address >> 8) };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ld", this);
			scope.addInstruction("LD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Ld_RR_NN.ARGUMENTS.check(arguments))
				return new Ld_RR_NN(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
