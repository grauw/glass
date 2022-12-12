package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Nop extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Nop_.ARGUMENTS.check(arguments))
			return new Nop_(address);
		throw new ArgumentException();
	}

	public static class Nop_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Nop_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x00);
		}

	}

}
