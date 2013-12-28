package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;

public class Res extends Instruction {
	
	private Expression argument1;
	private Expression argument2;
	
	public Res(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument2.getRegister(), 2);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int value = argument1.getInteger();
		if (value < 0 || value > 7)
			throw new ArgumentException();
		Register register = argument2.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0x80 | value << 3 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("res", this);
			scope.addInstruction("RES", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N_R.check(arguments))
				return new Res(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
