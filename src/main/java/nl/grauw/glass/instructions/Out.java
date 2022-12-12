package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Out extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Out_C_N.ARGUMENTS.check(arguments))
			return new Out_C_N(address, arguments.getElement(1));
		if (Out_N_N.ARGUMENTS.check(arguments))
			return new Out_N_N(address, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Out_C_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_C, Schema.DIRECT_R);

		private Expression argument;

		public Out_C_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x41 | argument.getRegister().get8BitCode() << 3);
		}

	}

	public static class Out_N_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);

		private Expression argument;

		public Out_N_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xD3, argument.getInteger());
		}

	}

}
