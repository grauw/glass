package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Db extends Instruction {
	
	private List<Expression> arguments;
	
	public Db(List<Expression> arguments) {
		this.arguments = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return arguments.size();
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		byte[] bytes = new byte[arguments.size()];
		for (int i = 0, length = arguments.size(); i < length; i++)
			bytes[i] = (byte)arguments.get(i).getInteger();
		return bytes;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("db", this);
			scope.addInstruction("DB", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (arguments != null)
				return new Db(arguments.getList());
			return null;
		}
		
	}
	
}
