package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Add_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Add_A(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyIndirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0x80 | register.get8BitCode()));
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("add", this);
			scope.addInstruction("ADD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Add_A.ARGUMENTS.check(arguments))
				return new Add_A(arguments.getElement(1));
			if (Add_A_N.ARGUMENTS.check(arguments))
				return new Add_A_N(arguments.getElement(1));
			if (Add_HL.ARGUMENTS.check(arguments))
				return new Add_HL(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
			return null;
		}
		
	}
	
}
