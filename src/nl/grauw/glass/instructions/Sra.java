package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;

public class Sra extends Instruction {
	
	private Expression argument;
	
	public Sra(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return indexifyIndirect(argument.getRegister(), 2);
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0x28 + register.get8BitCode()));
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("sra", this);
			scope.addInstruction("SRA", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Sra(arguments.getElement(0));
			return null;
		}
		
	}
	
}
