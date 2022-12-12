package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Sub extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Sub_R.ARGUMENTS.check(arguments))
			return new Sub_R(address, arguments);
		if (Sub_N.ARGUMENTS.check(arguments))
			return new Sub_N(address, arguments);
		throw new ArgumentException();
	}

	public static class Sub_R extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);

		private Expression argument;

		public Sub_R(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return indexifyIndirect(argument.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyIndirect(register, 0x90 | register.get8BitCode());
		}

	}

	public static class Sub_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);

		private Expression argument;

		public Sub_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xD6, argument.getInteger());
		}

	}

}
