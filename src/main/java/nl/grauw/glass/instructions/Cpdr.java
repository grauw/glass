package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Cpdr extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Cpdr_.ARGUMENTS.check(arguments))
			return new Cpdr_(address);
		throw new ArgumentException();
	}

	public static class Cpdr_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Cpdr_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xB9);
		}

	}

}
