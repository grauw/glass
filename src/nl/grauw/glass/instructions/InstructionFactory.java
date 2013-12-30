package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public abstract class InstructionFactory {
	
	public Instruction createInstruction(Expression arguments, Scope scope) {
		return createInstruction(arguments);
	}
	
	public abstract Instruction createInstruction(Expression arguments);
	
}
