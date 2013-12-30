package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;

public class Directive extends InstructionObject {
	
	@Override
	public int getSize(Scope context) {
		return 0;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] {};
	}
	
}
