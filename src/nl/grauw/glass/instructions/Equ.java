package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Equ extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Equ_N.ARGUMENTS.check(arguments))
			return new Equ_N();
		throw new ArgumentException();
	}
	
	public static class Equ_N extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.ANY);
		
	}
	
}
