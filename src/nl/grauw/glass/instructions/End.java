package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class End extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (End_.ARGUMENTS.check(arguments))
			return new End_();
		throw new ArgumentException();
	}
	
	public static class End_ extends Directive {
		
		public static Schema ARGUMENTS = new Schema();
		
	}
	
}
