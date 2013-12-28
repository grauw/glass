package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;

public class Xor extends Instruction {
	
	private Expression argument;
	
	public Xor(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0xA8 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("xor", this);
			scope.addInstruction("XOR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Xor(arguments);
			return null;
		}
		
	}
	
}
