package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Cp extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Cp(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyIndirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0xB8 | register.get8BitCode()));
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("cp", this);
			scope.addInstruction("CP", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Cp.ARGUMENTS.check(arguments))
				return new Cp(arguments);
			if (Cp_N.ARGUMENTS.check(arguments))
				return new Cp_N(arguments);
			return null;
		}
		
	}
	
}
