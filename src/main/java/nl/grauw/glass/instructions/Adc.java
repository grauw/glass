package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Adc extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Adc_A_R.ARGUMENTS.check(arguments))
			return new Adc_A_R(address, arguments.getElement(1));
		if (Adc_A_N.ARGUMENTS.check(arguments))
			return new Adc_A_N(address, arguments.getElement(1));
		if (Adc_HL_RR.ARGUMENTS.check(arguments))
			return new Adc_HL_RR(address, arguments.getElement(1));
		throw new ArgumentException();
	}

	public static class Adc_A_R extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);

		private Expression argument;

		public Adc_A_R(Expression address, Expression arguments) {
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
			return indexifyIndirect(register, 0x88 | register.get8BitCode());
		}

	}

	public static class Adc_A_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);

		private Expression argument;

		public Adc_A_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xCE, argument.getInteger());
		}

	}

	public static class Adc_HL_RR extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);

		private Expression argument;

		public Adc_HL_RR(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0x4A | argument.getRegister().get16BitCode() << 4);
		}

	}

}
