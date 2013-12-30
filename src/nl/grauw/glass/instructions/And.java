package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class And extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("and", this);
		scope.addInstruction("AND", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (And_R.ARGUMENTS.check(arguments))
			return new And_R(arguments);
		if (And_N.ARGUMENTS.check(arguments))
			return new And_N(arguments);
		throw new ArgumentException();
	}
	
	public static class And_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public And_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0xA0 | register.get8BitCode()));
		}
		
	}
	
	public static class And_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public And_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xE6, (byte)argument.getInteger() };
		}
		
	}
	
}
