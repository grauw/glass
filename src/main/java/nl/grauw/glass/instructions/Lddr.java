package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Lddr extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Lddr_.ARGUMENTS.check(arguments))
			return new Lddr_(address);
		throw new ArgumentException();
	}

	public static class Lddr_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Lddr_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xB8);
		}

	}

}
