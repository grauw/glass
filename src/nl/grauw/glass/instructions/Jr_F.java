package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Jr_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlagZC(), Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Jr_F(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Scope context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		int offset = argument2.getAddress() - (context.getAddress() + 2);
		if (offset < -128 || offset > 127)
			throw new ArgumentException("Jump offset out of range: " + offset);
		return new byte[] { (byte)(0x20 | argument1.getFlag().getCode() << 3), (byte)offset };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("jr", this);
			scope.addInstruction("JR", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Jr_F(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
