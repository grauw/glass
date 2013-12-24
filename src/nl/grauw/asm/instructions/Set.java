package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Set extends Instruction {
	
	private Expression argument1;
	private Expression argument2;
	
	public Set(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public byte[] getBytes() {
		int value = argument1.getInteger();
		if (value < 0 || value > 7)
			throw new ArgumentException();
		Register register = argument2.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0xC0 + value * 8 + register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "set";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N_R.check(arguments))
				return new Set(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
