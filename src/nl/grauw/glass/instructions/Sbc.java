package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Sbc extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Sbc_A_R.ARGUMENTS.check(arguments))
			return new Sbc_A_R(arguments.getElement(1));
		if (Sbc_A_N.ARGUMENTS.check(arguments))
			return new Sbc_A_N(arguments.getElement(1));
		if (Sbc_HL_RR.ARGUMENTS.check(arguments))
			return new Sbc_HL_RR(arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Sbc_A_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Sbc_A_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0x98 | register.get8BitCode()));
		}
		
	}
	
	public static class Sbc_A_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_N);
		
		private Expression argument;
		
		public Sbc_A_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xDE, (byte)argument.getInteger() };
		}
		
	}
	
	public static class Sbc_HL_RR extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);
		
		private Expression argument;
		
		public Sbc_HL_RR(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)(0x42 | argument.getRegister().get16BitCode() << 4) };
		}
		
	}
	
}
