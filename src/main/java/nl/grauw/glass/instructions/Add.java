package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Add extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Add_A_R.ARGUMENTS.check(arguments))
			return new Add_A_R(address, arguments.getElement(1));
		if (Add_A_N.ARGUMENTS.check(arguments))
			return new Add_A_N(address, arguments.getElement(1));
		if (Add_HL_RR.ARGUMENTS.check(arguments))
			return new Add_HL_RR(address, arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
		throw new ArgumentException();
	}

	public static class Add_A_R extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);

		private Expression argument;

		public Add_A_R(Expression address, Expression arguments) {
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
			return indexifyIndirect(register, 0x80 | register.get8BitCode());
		}

	}

	public static class Add_A_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);

		private Expression argument;

		public Add_A_N(Expression address, Expression arguments) {
			super(address);
			this.argument = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xC6, argument.getInteger());
		}

	}

	public static class Add_HL_RR extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL_IX_IY, Schema.DIRECT_RR_INDEX);

		private Register register1;
		private Register register2;

		public Add_HL_RR(Expression address, Register register1, Register register2) {
			super(address);
			this.register1 = register1;
			this.register2 = register2;

			if (register1.get16BitCode() == register2.get16BitCode() && register1 != register2)
				throw new ArgumentException();
		}

		@Override
		public Expression getSize() {
			return indexifyDirect(register1.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			return indexifyDirect(register1.getRegister(), 0x09 | register2.getRegister().get16BitCode() << 4);
		}

	}

}
