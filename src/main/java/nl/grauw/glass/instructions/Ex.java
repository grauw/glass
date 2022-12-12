package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ex extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Ex_AF.ARGUMENTS.check(arguments))
			return new Ex_AF(address);
		if (Ex_DE_HL.ARGUMENTS.check(arguments))
			return new Ex_DE_HL(address);
		if (Ex_SP.ARGUMENTS.check(arguments))
			return new Ex_SP(address, arguments.getElement(1));
		throw new ArgumentException();
	}

	public static class Ex_AF extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_AF, Schema.DIRECT_AF_);

		public Ex_AF(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0x08);
		}

	}

	public static class Ex_DE_HL extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);

		public Ex_DE_HL(Expression address) {
			super(address);
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ONE;
		}

		@Override
		public byte[] getBytes() {
			return b(0xEB);
		}

	}

	public static class Ex_SP extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_SP, Schema.DIRECT_HL_IX_IY);

		private Expression argument;

		public Ex_SP(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return indexifyDirect(argument.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			return indexifyDirect(argument.getRegister(), 0xE3);
		}

	}

}
