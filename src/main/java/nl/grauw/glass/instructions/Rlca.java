package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Rlca extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Rlca_.ARGUMENTS.check(arguments))
			return new Rlca_(address);
		throw new ArgumentException();
	}

	public static class Rlca_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Rlca_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x07);
		}

	}

}
