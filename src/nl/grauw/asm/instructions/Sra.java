package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Sra extends Instruction {
	
	private Expression argument;
	
	public Sra(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0x28 + register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "sra";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Sra(arguments.getElement(0));
			return null;
		}
		
	}
	
}
