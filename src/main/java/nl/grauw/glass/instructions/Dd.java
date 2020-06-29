package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;

public class Dd extends InstructionFactory {

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (arguments != null)
			return new Dd_N(context, arguments.getFlatList());
		throw new ArgumentException();
	}

	public static class Dd_N extends InstructionObject {

		private List<Expression> arguments;

		public Dd_N(Scope context, List<Expression> arguments) {
			super(context);
			this.arguments = arguments;
		}

		@Override
		public Expression getSize() {
			return IntegerLiteral.of(arguments.size() * 4);
		}

		@Override
		public byte[] getBytes() {
			byte[] bytes = new byte[arguments.size() * 4];
			for (int i = 0, length = arguments.size(); i < length; i++) {
				bytes[i * 4] = (byte)arguments.get(i).getInteger();
				bytes[i * 4 + 1] = (byte)(arguments.get(i).getInteger() >> 8);
				bytes[i * 4 + 2] = (byte)(arguments.get(i).getInteger() >> 16);
				bytes[i * 4 + 3] = (byte)(arguments.get(i).getInteger() >> 24);
			}
			return bytes;
		}

	}

}
