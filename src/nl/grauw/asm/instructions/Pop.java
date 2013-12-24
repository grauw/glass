package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Pop extends Instruction {
	
	Expression argument;
	
	public Pop(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		Register register = argument.getRegister();
		return indexifyDirect(register, (byte)(0xC1 | register.get16BitCode() << 4));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "pop";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_RR_AF_INDEX.check(arguments))
				return new Pop(arguments.getElement(0));
			return null;
		}
		
	}
	
}
