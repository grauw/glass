package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Sla extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("sla", this);
		scope.addInstruction("SLA", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Sla_R.ARGUMENTS.check(arguments))
			return new Sla_R(arguments.getElement(0));
		return null;
	}
	
	public static class Sla_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Sla_R(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument.getRegister();
			return indexifyIndirect(register, (byte)0xCB, (byte)(0x20 + register.get8BitCode()));
		}
		
	}
	
}
