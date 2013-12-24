package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Dec extends Instruction {
	
	private Expression argument;
	
	public Dec(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes() {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0x05 | register.get8BitCode() << 3));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "dec";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R.check(arguments))
				return new Dec(arguments.getElement(0));
			return null;
		}
		
	}
	
}
