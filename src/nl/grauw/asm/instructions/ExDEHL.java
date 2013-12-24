package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class ExDEHL extends Instruction {
	
	public static Schema ARGUMENTS_DE_HL = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xEB };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ex";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_DE_HL.check(arguments))
				return new ExDEHL();
			return null;
		}
		
	}
	
}
