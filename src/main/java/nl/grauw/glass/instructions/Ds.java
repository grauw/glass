package nl.grauw.glass.instructions;

import java.util.Arrays;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Ds extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Ds_N_N.ARGUMENTS_N.check(arguments))
			return new Ds_N_N(arguments.getElement(0), IntegerLiteral.ZERO);
		if (Ds_N_N.ARGUMENTS_N_N.check(arguments))
			return new Ds_N_N(arguments.getElement(0), arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Ds_N_N extends InstructionObject {
		
		public static Schema ARGUMENTS_N = new Schema(Schema.INTEGER);
		public static Schema ARGUMENTS_N_N = new Schema(Schema.INTEGER, Schema.INTEGER);
		
		private Expression size;
		private Expression value;
		
		public Ds_N_N(Expression size, Expression value) {
			this.size = size;
			this.value = value;
		}
		
		@Override
		public int getSize(Scope context) {
			return size.getInteger();
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			byte[] bytes = new byte[size.getInteger()];
			Arrays.fill(bytes, (byte)value.getInteger());
			return bytes;
		}
		
	}
	
}
