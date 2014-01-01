package nl.grauw.glass.instructions;

import java.io.IOException;
import java.io.OutputStream;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Org extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Org_N.ARGUMENTS.check(arguments))
			return new Org_N(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Org_N extends Empty.EmptyObject {
		
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
		
		@Override
		public int generateObjectCode(Scope context, int address, OutputStream output) throws IOException {
			return getAddress();
		}
		
	}
	
}
