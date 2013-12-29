package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_NN_RR extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_RR);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_NN_RR(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return 4;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int address = argument1.getAddress();
		return new byte[] { (byte)0xED, (byte)(0x43 | argument2.getRegister().get16BitCode() << 4),
				(byte)address, (byte)(address >> 8) };
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
				return new Ld_NN_RR(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
