package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Equ extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("equ", this);
		scope.addInstruction("EQU", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Equ_N.ARGUMENTS.check(arguments))
			return new Equ_N();
		return null;
	}
	
	public static class Equ_N extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.ANY);
		
	}
	
}
