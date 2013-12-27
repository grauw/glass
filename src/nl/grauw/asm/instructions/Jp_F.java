package nl.grauw.asm.instructions;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Jp_F extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);
	
	private Expression argument1;
	private Expression argument2;
	
	public Jp_F(Expression argument1, Expression argument2) {
		this.argument1 = argument1;
		this.argument2 = argument2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		int address = argument2.getAddress();
		return new byte[] { (byte)(0xC2 | argument1.getFlag().getCode() << 3), (byte)address, (byte)(address >> 8) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "jp";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Jp_F(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
