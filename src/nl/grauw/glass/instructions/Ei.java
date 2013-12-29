package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.Expression;

public class Ei extends Instruction {
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xFB };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ei", this);
			scope.addInstruction("EI", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Ei();
			return null;
		}
		
	}
	
}