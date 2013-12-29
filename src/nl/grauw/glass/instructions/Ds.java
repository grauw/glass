package nl.grauw.glass.instructions;

import java.util.Arrays;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

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
	public int getSize(Scope context) {
		return size.getInteger();
	}
	
	@Override
	public byte[] getBytes(Scope context) {
		byte[] bytes = new byte[size.getInteger()];
		Arrays.fill(bytes, (byte)value.getInteger());
		return bytes;
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("ds", this);
			scope.addInstruction("DS", this);
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
