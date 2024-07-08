package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ldir extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Ldir_.ARGUMENTS.check(arguments))
			return new Ldir_(context);
		throw new ArgumentException();
	}

	public static class Ldir_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ldir_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xB0);
		}

	}

}
