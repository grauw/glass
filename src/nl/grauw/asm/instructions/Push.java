package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Push extends Instruction {
	
	Expression argument;
	
	public Push(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public byte[] getBytes() {
		Register register = argument.getRegister();
		return indexifyDirect(register, (byte)(0xC5 | register.get16BitCode() << 4));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "push";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_RR_AF_INDEX.check(arguments))
				return new Push(arguments.getElement(0));
			return null;
		}
		
	}
	
}
