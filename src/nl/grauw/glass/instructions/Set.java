package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Set extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("set", this);
		scope.addInstruction("SET", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Set_N_R.ARGUMENTS.check(arguments))
			return new Set_N_R(arguments.getElement(0), arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Set_N_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Expression argument1;
		private Expression argument2;
		
		public Set_N_R(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument2.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int value = argument1.getInteger();
			if (value < 0 || value > 7)
				throw new ArgumentException();
			Register register = argument2.getRegister();
			return indexifyIndirect(register, (byte)0xCB, (byte)(0xC0 | value << 3 | register.get8BitCode()));
		}
		
	}
	
}
