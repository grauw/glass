package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Warning extends Instruction {
	
	public static Schema ARGUMENTS = new Schema();
	public static Schema ARGUMENTS_S = new Schema(Schema.STRING);
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (ARGUMENTS.check(arguments) || ARGUMENTS_S.check(arguments))
			return new Warning_(arguments);
		throw new ArgumentException();
	}
	
	public static class Warning_ extends Empty.EmptyObject {
		
		private final Expression argument;
		
		public Warning_(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			if (argument == null)
				System.out.println("Warning directive was encountered.");
			else
				System.out.println(argument.getString());
			return super.getBytes(context);
		}
		
	}
	
}
