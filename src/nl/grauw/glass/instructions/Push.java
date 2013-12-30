package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Push extends InstructionFactory {
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Push_RR.ARGUMENTS.check(arguments))
			return new Push_RR(arguments.getElement(0));
		throw new ArgumentException();
	}
	
	public static class Push_RR extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_AF_INDEX);
		
		Expression argument;
		
		public Push_RR(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyDirect(register, (byte)(0xC5 | register.get16BitCode() << 4));
		}
		
	}
	
}
