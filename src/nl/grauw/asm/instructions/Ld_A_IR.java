package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_A_IR extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_IR);
	
	private Expression argument;
	
	public Ld_A_IR(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		if (argument.getRegister() == Register.I)
			return new byte[] { (byte)0xED, (byte)0x57 };
		return new byte[] { (byte)0xED, (byte)0x5F };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_A_IR(arguments.getElement(1));
			return null;
		}
		
	}
	
}
