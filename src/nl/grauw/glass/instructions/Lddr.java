package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Lddr extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)0xB8 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("lddr", this);
			scope.addInstruction("LDDR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Lddr.ARGUMENTS.check(arguments))
				return new Lddr();
			return null;
		}
		
	}
	
}
