package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;

public class Jr extends Instruction {
	
	private Expression argument;
	
	public Jr(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int offset = argument.getAddress() - (context.getAddress() + 2);
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Jump offset out of range: " + offset);
		return new byte[] { (byte)0x18, (byte)offset };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("jr", this);
			scope.addInstruction("JR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Jr(arguments.getElement(0));
			return null;
		}
		
	}
	
}
