package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_SP_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_SP, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Ld_SP_HL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return indexifyDirect(argument.getRegister(), (byte)0xF9);
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_SP_HL(arguments.getElement(1));
			return null;
		}
		
	}
	
}
