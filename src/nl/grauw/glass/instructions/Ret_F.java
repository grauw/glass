package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ret_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlag());
	
	private Expression argument;
	
	public Ret_F(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)(0xC0 | argument.getFlag().getCode() << 3) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ret", this);
			scope.addInstruction("RET", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ret_F(arguments.getElement(0));
			return null;
		}
		
	}
	
}
