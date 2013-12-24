package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class AddHL extends Instruction {
	
	private Expression argument1;
	private Expression argument2;
	
	public AddHL(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public byte[] getBytes() {
		return indexifyDirect(argument1.getRegister(), (byte)(0x09 | argument2.getRegister().get16BitCode() << 4));
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "add";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_HLIXIY_RR.check(arguments))
				return new AddHL(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
