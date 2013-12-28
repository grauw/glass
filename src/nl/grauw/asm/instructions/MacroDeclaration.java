package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Expression;

public class MacroDeclaration extends Directive {
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("macro", this);
			scope.addInstruction("MACRO", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new MacroDeclaration();
		}
		
	}
	
}
