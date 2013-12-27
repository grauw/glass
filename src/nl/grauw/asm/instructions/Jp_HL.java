package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Jp_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_HL_IX_IY);
	public static Schema ARGUMENTS_ALT = new Schema(Schema.DIRECT_HL_IX_IY);
	
	private Expression argument;
	
	public Jp_HL(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return indexifyDirect(argument.getRegister(), 1);
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return indexifyDirect(argument.getRegister(), (byte)0xE9);
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "jp";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments) || ARGUMENTS_ALT.check(arguments))
				return new Jp_HL(arguments.getElement(0));
			return null;
		}
		
	}
	
}
