package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;

public class MacroDeclaration extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		return new MacroDeclaration_();
	}
	
	public static class MacroDeclaration_ extends Directive {
		
	}
	
}
