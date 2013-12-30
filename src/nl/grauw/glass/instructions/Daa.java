package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Daa extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0x27 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("daa", this);
			scope.addInstruction("DAA", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Daa.ARGUMENTS.check(arguments))
				return new Daa();
			return null;
		}
		
	}
	
}
