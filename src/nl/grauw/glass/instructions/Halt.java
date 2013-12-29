package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Halt extends Instruction {
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0x76 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("halt", this);
			scope.addInstruction("HALT", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Halt();
			return null;
		}
		
	}
	
}
