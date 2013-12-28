package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;

public class Ld_A_BCDE extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_BC_DE);
	
	private Expression argument;
	
	public Ld_A_BCDE(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)(0x0A | argument.getRegister().get16BitCode() << 4) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ld", this);
			scope.addInstruction("LD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_A_BCDE(arguments.getElement(1));
			return null;
		}
		
	}
	
}
