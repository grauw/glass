package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Org extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("org", this);
		scope.addInstruction("ORG", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Org_N.ARGUMENTS.check(arguments))
			return new Org_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Org_N extends Directive {
		
		public static Schema ARGUMENTS = new Schema(Schema.INTEGER);
		
		private Expression argument;
		
		public Org_N(Expression argument) {
			this.argument = argument;
		}
		
		public int getAddress() {
			return argument.getAddress();
		}
		
		@Override
		public int resolve(Scope context, int address) {
			return super.resolve(context, getAddress());
		}
		
	}
	
}
