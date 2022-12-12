package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Muluw extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Muluw_RR_RR.ARGUMENTS.check(arguments))
			return new Muluw_RR_RR(address, arguments.getElement(1));
		throw new ArgumentException();
	}

	public static class Muluw_RR_RR extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);

		private Expression argument;

		public Muluw_RR_RR(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.TWO;
		}

		@Override
		public byte[] getBytes() {
			return b(0xED, 0xC3 | argument.getRegister().get16BitCode() << 4);
		}

	}

}
