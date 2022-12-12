package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Jp extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Jp_F_N.ARGUMENTS.check(arguments))
			return new Jp_F_N(address, arguments.getElement(0), arguments.getElement(1));
		if (Jp_HL.ARGUMENTS.check(arguments) || Jp_HL.ARGUMENTS_ALT.check(arguments))
			return new Jp_HL(address, arguments.getElement(0));
		if (Jp_N.ARGUMENTS.check(arguments))
			return new Jp_N(address, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Jp_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);

		private Expression argument;

		public Jp_N(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.THREE;
		}

		@Override
		public byte[] getBytes() {
			int address = argument.getInteger();
			return b(0xC3, address, address >> 8);
		}

	}

	public static class Jp_F_N extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);

		private Expression argument1;
		private Expression argument2;

		public Jp_F_N(Expression address, Expression argument1, Expression argument2) {
			super(address);
			this.argument1 = argument1;
			this.argument2 = argument2;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.THREE;
		}

		@Override
		public byte[] getBytes() {
			int address = argument2.getInteger();
			return b(0xC2 | argument1.getFlag().getCode() << 3, address, address >> 8);
		}

	}

	public static class Jp_HL extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_HL_IX_IY);
		public static Schema ARGUMENTS_ALT = new Schema(Schema.DIRECT_HL_IX_IY);

		private Expression argument;

		public Jp_HL(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return indexifyDirect(argument.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			return indexifyDirect(argument.getRegister(), 0xE9);
		}

	}

}
