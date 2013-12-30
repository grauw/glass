package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Include extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Include_S.ARGUMENTS.check(arguments))
			return new Include_S();
		throw new ArgumentException();
	}
	
	public static class Include_S extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.STRING);
		
	}
	
}
