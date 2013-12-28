package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Org extends Directive {
	
	public static Schema ARGUMENTS = new Schema(Schema.INTEGER);
	
	private Expression argument;
	
	public Org(Expression argument) {
		this.argument = argument;
	}
	
	public int getAddress() {
		return argument.getAddress();
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("org", this);
			scope.addInstruction("ORG", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Org(arguments.getElement(0));
			return null;
		}
		
	}
	
}
