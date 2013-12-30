package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Rrd extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)0xED, (byte)0x67 };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("rrd", this);
			scope.addInstruction("RRD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Rrd.ARGUMENTS.check(arguments))
				return new Rrd();
			return null;
		}
		
	}
	
}
