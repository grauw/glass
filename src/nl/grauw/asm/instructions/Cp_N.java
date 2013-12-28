package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;

public class Cp_N extends Instruction {
	
	private Expression argument;
	
	public Cp_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xFE, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("cp", this);
			scope.addInstruction("CP", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Cp_N(arguments);
			return null;
		}
		
	}
	
}
