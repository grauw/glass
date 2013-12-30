package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Cpdr extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)0xB9 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("cpdr", this);
			scope.addInstruction("CPDR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Cpdr.ARGUMENTS.check(arguments))
				return new Cpdr();
			return null;
		}
		
	}
	
}
