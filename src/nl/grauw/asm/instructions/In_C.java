package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class In_C extends Instruction {
	
	public static Schema ARGUMENTS_R_C = new Schema(Schema.DIRECT_R, Schema.INDIRECT_C);
	public static Schema ARGUMENTS_C = new Schema(Schema.INDIRECT_C);
	
	private Expression argument;
	
	public In_C(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public byte[] getBytes() {
		return new byte[] { (byte)0xED, (byte)(0x40 | argument.getRegister().get8BitCode() << 3) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "in";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_R_C.check(arguments))
				return new In_C(arguments.getElement(0));
			if (ARGUMENTS_C.check(arguments))
				return new In_C(Register.HL);
			return null;
		}
		
	}
	
}
