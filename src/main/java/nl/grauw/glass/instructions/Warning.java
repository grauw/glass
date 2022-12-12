package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Warning extends InstructionFactory {

	public static Schema ARGUMENTS = new Schema();
	public static Schema ARGUMENTS_S = new Schema(Schema.STRING);

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (ARGUMENTS.check(arguments) || ARGUMENTS_S.check(arguments))
			return new Warning_(address, arguments);
		throw new ArgumentException();
	}

	public static class Warning_ extends Empty.EmptyObject {

		private final Expression argument;

		public Warning_(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public byte[] getBytes() {
			if (argument == null)
				System.out.println("Warning: A warning directive was encountered.");
			else
				System.out.println("Warning: " + argument.getString());
			return super.getBytes();
		}

	}

}
