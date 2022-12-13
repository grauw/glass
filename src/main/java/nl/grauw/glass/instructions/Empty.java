package nl.grauw.glass.instructions;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;

public class Empty extends InstructionFactory {

	public static final Empty INSTANCE = new Empty();

	@Override
	public InstructionObject createObject(Expression address, Expression arguments) {
		return new EmptyObject(address);
	}

	public static class EmptyObject extends InstructionObject {

		public EmptyObject(Expression address) {
			super(address);
		}

		@Override
		public Expression resolve() {
			return address;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.ZERO;
		}

		@Override
		public byte[] getBytes() {
			return b();
		}

	}

}
