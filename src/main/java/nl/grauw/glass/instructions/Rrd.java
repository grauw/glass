package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Rrd extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Rrd_.ARGUMENTS.check(arguments))
			return new Rrd_(address);
		throw new ArgumentException();
	}

	public static class Rrd_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Rrd_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x67);
		}

	}

}
