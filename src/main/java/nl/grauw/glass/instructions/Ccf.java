package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ccf extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Ccf_.ARGUMENTS.check(arguments))
			return new Ccf_(address);
		throw new ArgumentException();
	}

	public static class Ccf_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ccf_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x3F);
		}

	}

}
