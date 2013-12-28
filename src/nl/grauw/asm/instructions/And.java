package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;

public class And extends Instruction {
	
	private Expression argument;
	
	public And(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0xA0 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("and", this);
			scope.addInstruction("AND", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new And(arguments);
			return null;
		}
		
	}
	
}
