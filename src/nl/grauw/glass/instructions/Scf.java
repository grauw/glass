package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Scf extends Instruction {
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0x37 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("scf", this);
			scope.addInstruction("SCF", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_NONE.check(arguments))
				return new Scf();
			return null;
		}
		
	}
	
}
