package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Out_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Out_N(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xD3, (byte)argument.getInteger() };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("out", this);
			scope.addInstruction("OUT", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Out_N(arguments.getElement(0));
			return null;
		}
		
	}
	
}
