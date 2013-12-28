package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public interface InstructionFactory {
	
	public abstract void register(Scope scope);
	
	public abstract Instruction createInstruction(Expression arguments);
	
}