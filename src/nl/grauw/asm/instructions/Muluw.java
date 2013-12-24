package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Muluw extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);
	
	private Expression argument;
	
	public Muluw(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0xC3 | argument.getRegister().get16BitCode() << 4) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "muluw";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Muluw(arguments.getElement(1));
			return null;
		}
		
	}
	
}
