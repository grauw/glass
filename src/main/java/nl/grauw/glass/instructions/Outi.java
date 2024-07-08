package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Outi extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Outi_.ARGUMENTS.check(arguments))
			return new Outi_(context);
		throw new ArgumentException();
	}

	public static class Outi_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Outi_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xA3);
		}

	}

}
