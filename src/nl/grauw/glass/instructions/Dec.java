package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Dec extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("dec", this);
		scope.addInstruction("DEC", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Dec_R.ARGUMENTS.check(arguments))
			return new Dec_R(arguments.getElement(0));
		if (Dec_RR.ARGUMENTS.check(arguments))
			return new Dec_RR(arguments.getElement(0));
		return null;
	}
	
	public static class Dec_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Dec_R(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)(0x05 | register.get8BitCode() << 3));
		}
		
	}
	
	public static class Dec_RR extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX);
		
		private Expression argument;
		
		public Dec_RR(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyDirect(register, (byte)(0x0B | register.get16BitCode() << 4));
		}
		
	}
	
}
