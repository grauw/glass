package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Pop extends InstructionFactory {

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		if (Pop_RR.ARGUMENTS.check(arguments))
			return new Pop_RR(address, arguments.getElement(0));
		throw new ArgumentException();
	}

	public static class Pop_RR extends InstructionObject {

		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_AF_INDEX);

		Expression argument;

		public Pop_RR(Expression address, Expression argument) {
			super(address);
			this.argument = argument;
		}

		@Override
		public Expression getSize() {
			return indexifyDirect(argument.getRegister(), IntegerLiteral.ONE);
		}

		@Override
		public byte[] getBytes() {
			Register register = argument.getRegister();
			return indexifyDirect(register, 0xC1 | register.get16BitCode() << 4);
		}

	}

}
