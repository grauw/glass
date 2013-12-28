package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class MacroDeclaration extends Directive {
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(InstructionRegistry registry) {
			registry.add("macro", this);
			registry.add("MACRO", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			return new MacroDeclaration();
		}
		
	}
	
}
