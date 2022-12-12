package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ret extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Ret_.ARGUMENTS.check(arguments))
			return new Ret_(address);
		if (Ret_F.ARGUMENTS.check(arguments))
			return new Ret_F(address, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Ret_ extends InstructionObject {

		public static Schema ARGUMENTS = new Schema();

		public Ret_(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0xC9);
		}

	}

	public static class Ret_F extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag());

		private Expression argument;

		public Ret_F(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0xC0 | argument.getFlag().getCode() << 3);
		}

	}

}
