package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ini extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Ini_.ARGUMENTS.check(arguments))
			return new Ini_(address);
		throw new ArgumentException();
	}

	public static class Ini_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ini_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xA2);
		}

	}

}
