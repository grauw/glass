package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Xor extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("xor", this);
		scope.addInstruction("XOR", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Xor_R.ARGUMENTS.check(arguments))
			return new Xor_R(arguments);
		if (Xor_N.ARGUMENTS.check(arguments))
			return new Xor_N(arguments);
		return null;
	}
	
	public static class Xor_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Xor_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0xA8 | register.get8BitCode()));
		}
		
	}
	
	public static class Xor_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Xor_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xEE, (byte)argument.getInteger() };
		}
		
	}
	
}
