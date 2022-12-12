package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Halt extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Halt_.ARGUMENTS.check(arguments))
			return new Halt_(address);
		throw new ArgumentException();
	}

	public static class Halt_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Halt_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x76);
		}

	}

}
