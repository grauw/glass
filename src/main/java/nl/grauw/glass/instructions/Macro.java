package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;

public class Macro extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		return Empty.EmptyObject.INSTANCE;
	}
	
}
