package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Equ extends Directive {
	
	public static Schema ARGUMENTS = new Schema(Schema.ANY);
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("equ", this);
			scope.addInstruction("EQU", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Equ();
			return null;
		}
		
	}
	
}
