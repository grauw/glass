package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ld_BCDE_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_BC_DE, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Ld_BCDE_A(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Scope context) {
		return 1;
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		return new byte[] { (byte)(0x02 | argument.getRegister().get16BitCode() << 4) };
	}
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ld", this);
			scope.addInstruction("LD", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Ld_BCDE_A.ARGUMENTS.check(arguments))
				return new Ld_BCDE_A(arguments.getElement(0));
			return null;
		}
		
	}
	
}
