package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Dw extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (arguments != null)
			return new Dw_N(arguments.getList());
		throw new ArgumentException();
	}
	
	public static class Dw_N extends Instruction {
		
		private List<Expression> arguments;
		
		public Dw_N(List<Expression> arguments) {
			this.arguments = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return arguments.size() * 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			byte[] bytes = new byte[arguments.size() * 2];
			for (int i = 0, length = arguments.size(); i < length; i++) {
				bytes[i * 2] = (byte)arguments.get(i).getInteger();
				bytes[i * 2 + 1] = (byte)(arguments.get(i).getInteger() >> 8);
			}
			return bytes;
		}
		
	}
	
}
