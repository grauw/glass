package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ld_IR_A extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_IR, Schema.DIRECT_A);
	
	private Expression argument;
	
	public Ld_IR_A(Expression argument) {
		this.argument = argument;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		if (argument.getRegister() == Register.I)
			return new byte[] { (byte)0xED, (byte)0x47 };
		return new byte[] { (byte)0xED, (byte)0x4F };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ld";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Ld_IR_A(arguments.getElement(0));
			return null;
		}
		
	}
	
}
