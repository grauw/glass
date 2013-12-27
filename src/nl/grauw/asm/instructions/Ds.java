package nl.grauw.asm.instructions;

import java.util.Arrays;

import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.IntegerLiteral;
import nl.grauw.asm.expressions.Schema;
import nl.grauw.asm.instructions.InstructionRegistry.InstructionFactory;

public class Ds extends Instruction {
	
	public static Schema ARGUMENTS_N = new Schema(Schema.INTEGER);
	public static Schema ARGUMENTS_N_N = new Schema(Schema.INTEGER, Schema.INTEGER);
	
	private Expression size;
	private Expression value;
	
	public Ds(Expression size, Expression value) {
		this.size = size;
		this.value = value;
	}
	
	@Override
	public int getSize(Context context) {
		return size.getInteger();
	}
	
	@Override
	public byte[] getBytes(Context context) {
		byte[] bytes = new byte[size.getInteger()];
		Arrays.fill(bytes, (byte)value.getInteger());
		return bytes;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public String getMnemonic() {
			return "ds";
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS_N.check(arguments))
				return new Ds(arguments.getElement(0), IntegerLiteral.ZERO);
			if (ARGUMENTS_N_N.check(arguments))
				return new Ds(arguments.getElement(0), arguments.getElement(1));
			return null;
		}
		
	}
	
}
