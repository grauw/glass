package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Empty extends Instruction {
	
	public static final Empty INSTANCE = new Empty();
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		return EmptyObject.INSTANCE;
	}
	
	public static class EmptyObject extends InstructionObject {
		
		public static final EmptyObject INSTANCE = new EmptyObject();
		private static final byte[] NO_BYTES = new byte[] {};
		
		@Override
		public int getSize(Scope context) {
			return 0;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return NO_BYTES;
		}
		
	}
	
}
