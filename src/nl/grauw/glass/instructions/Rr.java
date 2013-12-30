package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Rr extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Rr_R.ARGUMENTS.check(arguments))
			return new Rr_R(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Rr_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Rr_R(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)0xCB, (byte)(0x18 + register.get8BitCode()));
		}
		
	}
	
}
