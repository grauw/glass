package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Rld extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Rld_.ARGUMENTS.check(arguments))
			return new Rld_(context);
		throw new ArgumentException();
	}

	public static class Rld_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Rld_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x6F);
		}

	}

}
