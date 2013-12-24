package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class ExAF extends Instruction {
	
	public static Schema ARGUMENTS_AF_AF_ = new Schema(Schema.DIRECT_AF, Schema.DIRECT_AF_);
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0x08 };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ex";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_AF_AF_.check(arguments))
				return new ExAF();
			return null;
		}
		
	}
	
}
