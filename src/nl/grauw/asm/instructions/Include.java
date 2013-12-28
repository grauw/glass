package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Include extends Directive {
	
	public static Schema ARGUMENTS = new Schema(Schema.STRING);
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("include", this);
			scope.addInstruction("INCLUDE", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Include();
			return null;
		}
		
	}
	
}
