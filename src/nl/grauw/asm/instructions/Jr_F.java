package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Jr_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlagZC(), Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Jr_F(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
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
