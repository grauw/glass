package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Or extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("or", this);
		scope.addInstruction("OR", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Or_R.ARGUMENTS.check(arguments))
			return new Or_R(arguments);
		if (Or_N.ARGUMENTS.check(arguments))
			return new Or_N(arguments);
		throw new ArgumentException();
	}
	
	public static class Or_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Or_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0xB0 | register.get8BitCode()));
		}
		
	}
	
	public static class Or_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Or_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xF6, (byte)argument.getInteger() };
		}
		
	}
	
}
