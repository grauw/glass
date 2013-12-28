package nl.grauw.asm.instructions;

import java.util.List;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;

public class Dw extends Instruction {
	
	private List<Expression> arguments;
	
	public Dw(List<Expression> arguments) {
		this.arguments = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return arguments.size() * 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		byte[] bytes = new byte[arguments.size() * 2];
		for (int i = 0, length = arguments.size(); i < length; i++) {
			bytes[i * 2] = (byte)arguments.get(i).getInteger();
			bytes[i * 2 + 1] = (byte)(arguments.get(i).getInteger() >> 8);
		}
		return bytes;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("dw", this);
			scope.addInstruction("DW", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (arguments != null)
				return new Dw(arguments.getList());
			return null;
		}
		
	}
	
}
