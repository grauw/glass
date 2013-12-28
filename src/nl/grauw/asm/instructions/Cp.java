package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;

public class Cp extends Instruction {
	
	private Expression argument;
	
	public Cp(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0xB8 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("cp", this);
			scope.addInstruction("CP", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Cp(arguments);
			return null;
		}
		
	}
	
}
