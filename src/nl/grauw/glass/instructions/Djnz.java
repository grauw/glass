package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public class Djnz extends Instruction {
	
	private Expression argument;
	
	public Djnz(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int offset = argument.getAddress() - (context.getAddress() + 2);
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Jump offset out of range: " + offset);
		return new byte[] { (byte)0x10, (byte)offset };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("djnz", this);
			scope.addInstruction("DJNZ", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Djnz(arguments);
			return null;
		}
		
	}
	
}
