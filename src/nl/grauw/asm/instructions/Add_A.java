package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Add_A extends Instruction {
	
	private Expression argument;
	
	public Add_A(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		Register register = argument.getRegister();
		return indexifyIndirect(register, (byte)(0x80 | register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "add";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_A_R.check(arguments))
				return new Add_A(arguments.getElement(1));
			return null;
		}
		
	}
	
}