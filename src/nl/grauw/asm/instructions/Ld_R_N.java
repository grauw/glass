package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;

public class Ld_R_N extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY, Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Ld_R_N(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyIndirect(argument1.getRegister(), 2);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument1.getRegister();
		return indexifyIndirect(register, (byte)(0x06 | register.get8BitCode() << 3), (byte)argument2.getInteger());
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
				return new Ld_R_N(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
