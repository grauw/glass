package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Sla extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Sla_R.ARGUMENTS.check(arguments))
			return new Sla_R(address, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Sla_R extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);

		private Expression argument;

		public Sla_R(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return indexifyIndirect(argument.getRegister(), IntegerLiteral.TWO);
		}

		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyOnlyIndirect(register, 0xCB, 0x20 | register.get8BitCode());
		}

	}

}
