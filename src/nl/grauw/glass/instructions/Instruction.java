package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public abstract class Instruction {
	
	public InstructionObject createObject(Expression arguments, Scope scope) {
		return createObject(arguments);
	}
	
	public abstract InstructionObject createObject(Expression arguments);
	
}
