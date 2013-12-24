package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Bit extends Instruction {
	
	private Expression argument1;
	private Expression argument2;
	
	public Bit(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public byte[] getBytes() {
		int value = argument1.getInteger();
		if (value < 0 || value > 7)
			throw new ArgumentException();
		Register register = argument2.getRegister();
		return indexifyIndirect(register, (byte)0xCB, (byte)(0x40 + value * 8 + register.get8BitCode()));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "bit";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N_R.check(arguments))
				return new Bit(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
