package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Otir extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Otir_.ARGUMENTS.check(arguments))
			return new Otir_(address);
		throw new ArgumentException();
	}

	public static class Otir_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Otir_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xB3);
		}

	}

}
