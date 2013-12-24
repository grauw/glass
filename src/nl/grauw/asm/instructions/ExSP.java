package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class ExSP extends Instruction {
	
	public static Schema ARGUMENTS_SP_HLIXIY = new Schema(Schema.INDIRECT_SP, Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public ExSP(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		return indexifyDirect(argument.getRegister(), (byte)0xE3);
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ex";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_SP_HLIXIY.check(arguments))
				return new ExSP(arguments.getElement(1));
			return null;
		}
		
	}
	
}
