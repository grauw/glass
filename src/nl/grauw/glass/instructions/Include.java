package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Include extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Include_S.ARGUMENTS.check(arguments))
			return new Include_S();
		throw new ArgumentException();
	}
	
	public static class Include_S extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.STRING);
		
	}
	
}
