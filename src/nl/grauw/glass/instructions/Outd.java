package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Outd extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)0xAB };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("outd", this);
			scope.addInstruction("OUTD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Outd.ARGUMENTS.check(arguments))
				return new Outd();
			return null;
		}
		
	}
	
}
