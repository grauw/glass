package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;

public class Exx extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xD9 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("exx", this);
			scope.addInstruction("EXX", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Exx();
			return null;
		}
		
	}
	
}
