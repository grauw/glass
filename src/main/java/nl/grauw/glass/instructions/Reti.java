package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Reti extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Reti_.ARGUMENTS.check(arguments))
			return new Reti_(address);
		throw new ArgumentException();
	}

	public static class Reti_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Reti_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x4D);
		}

	}

}
