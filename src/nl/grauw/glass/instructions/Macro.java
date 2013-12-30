package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;

public class Macro extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		return new Macro_();
	}
	
	public static class Macro_ extends Directive {
		
	}
	
}
