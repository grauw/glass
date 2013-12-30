package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Include extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("include", this);
		scope.addInstruction("INCLUDE", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Include_S.ARGUMENTS.check(arguments))
			return new Include_S();
		return null;
	}
	
	public static class Include_S extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.STRING);
		
	}
	
}
