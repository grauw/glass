package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Expression;

public interface InstructionFactory {
	
	public abstract void register(Scope scope);
	
	public abstract Instruction createInstruction(Expression arguments);
	
}