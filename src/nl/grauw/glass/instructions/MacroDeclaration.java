package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class MacroDeclaration extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("macro", this);
		scope.addInstruction("MACRO", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		return new MacroDeclaration_();
	}
	
	public static class MacroDeclaration_ extends Directive {
		
	}
	
}
