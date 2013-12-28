package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;

public class Rlc extends Instruction {
	
	private Expression argument;
	
	public Rlc(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument.getRegister(), 2);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0x00 + register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("rlc", this);
			scope.addInstruction("RLC", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Rlc(arguments.getElement(0));
			return null;
		}
		
	}
	
}
