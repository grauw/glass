package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Include extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.STRING);
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return Empty.EmptyObject.INSTANCE;
		throw new ArgumentException();
	}
	
}
