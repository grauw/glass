package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Else extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return Empty.EmptyObject.INSTANCE;
		throw new ArgumentException();
	}
	
}
