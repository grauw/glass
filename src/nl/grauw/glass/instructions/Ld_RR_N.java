package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Ld_RR_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX, Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_RR_N(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyDirect(argument1.getRegister(), 3);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		Register register = argument1.getRegister();
		return indexifyDirect(register, (byte)(0x01 | register.get16BitCode() << 4),
				(byte)argument2.getInteger(), (byte)(argument2.getInteger() >> 8));
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
				return new Ld_RR_N(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
