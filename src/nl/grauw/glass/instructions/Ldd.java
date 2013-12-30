package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ldd extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)0xA8 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ldd", this);
			scope.addInstruction("LDD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Ldd.ARGUMENTS.check(arguments))
				return new Ldd();
			return null;
		}
		
	}
	
}
