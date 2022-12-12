package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class In extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (In_N_C.ARGUMENTS.check(arguments))
			return new In_N_C(address, arguments.getElement(0));
		if (In_N_C.ARGUMENTS_NO_R.check(arguments))
			return new In_N_C(address, Register.HL);
		if (In_N_N.ARGUMENTS.check(arguments))
			return new In_N_N(address, arguments.getElement(1));
		throw new ArgumentException();
	}

	public static class In_N_C extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R, Schema.INDIRECT_C);
		public static Schema ARGUMENTS_NO_R = new Schema(Schema.INDIRECT_C);

		private Expression argument;

		public In_N_C(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x40 | argument.getRegister().get8BitCode() << 3);
		}

	}

	public static class In_N_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);

		private Expression argument;

		public In_N_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xDB, argument.getInteger());
		}

	}

}
