package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Cp extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("cp", this);
		scope.addInstruction("CP", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Cp_R.ARGUMENTS.check(arguments))
			return new Cp_R(arguments);
		if (Cp_N.ARGUMENTS.check(arguments))
			return new Cp_N(arguments);
		throw new ArgumentException();
	}
	
	public static class Cp_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Cp_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0xB8 | register.get8BitCode()));
		}
		
	}
	
	public static class Cp_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Cp_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xFE, (byte)argument.getInteger() };
		}
		
	}
	
}
