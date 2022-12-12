package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Scf extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Scf_.ARGUMENTS.check(arguments))
			return new Scf_(address);
		throw new ArgumentException();
	}

	public static class Scf_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Scf_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x37);
		}

	}

}
