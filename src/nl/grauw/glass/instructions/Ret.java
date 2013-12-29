package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.Expression;

public class Ret extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xC9 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ret", this);
			scope.addInstruction("RET", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Ret();
			return null;
		}
		
	}
	
}