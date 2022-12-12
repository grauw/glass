package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Di extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Di_.ARGUMENTS.check(arguments))
			return new Di_(address);
		throw new ArgumentException();
	}

	public static class Di_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Di_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0xF3);
		}

	}

}
