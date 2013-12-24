package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ex_DE_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xEB };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ex";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ex_DE_HL();
			return null;
		}
		
	}
	
}
