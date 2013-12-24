package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Mulub extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R);
	
	private Expression argument;
	
	public Mulub(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0xC1 | argument.getRegister().get8BitCode() << 3) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "mulub";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Mulub(arguments.getElement(1));
			return null;
		}
		
	}
	
}
