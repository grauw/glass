package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Endm extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Endm_.ARGUMENTS.check(arguments))
			return new Endm_();
		throw new ArgumentException();
	}
	
	public static class Endm_ extends Directive {
		
		public static Schema ARGUMENTS = new Schema();
		
	}
	
}
