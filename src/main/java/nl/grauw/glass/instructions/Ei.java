package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ei extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (Ei_.ARGUMENTS.check(arguments))
			return new Ei_(context);
		throw new ArgumentException();
	}

	public static class Ei_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ei_(Scope context) {
			super(context);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return new byte[] { (byte)0xFB };
		}

	}

}
