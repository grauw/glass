package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;
import nl.grauw.glass.expressions.Subtract;

public class Djnz extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Djnz_N.ARGUMENTS.check(arguments))
			return new Djnz_N(address, arguments);
		throw new ArgumentException();
	}

	public static class Djnz_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);

		private Expression argument;

		public Djnz_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			int offset = new Subtract(argument, resolve()).getInteger();
			if (offset < -128 || offset > 127)
				throw new ArgumentException("Jump offset out of range: " + offset);
			return b(0x10, offset);
		}

	}

}
