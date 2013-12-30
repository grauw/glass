package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class In extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("in", this);
		scope.addInstruction("IN", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (In_N_C.ARGUMENTS.check(arguments))
			return new In_N_C(arguments.getElement(0));
		if (In_N_C.ARGUMENTS_NO_R.check(arguments))
			return new In_N_C(Register.HL);
		if (In_N_N.ARGUMENTS.check(arguments))
			return new In_N_N(arguments.getElement(1));
		return null;
	}
	
	public static class In_N_C extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R, Schema.INDIRECT_C);
		public static Schema ARGUMENTS_NO_R = new Schema(Schema.INDIRECT_C);
		
		private Expression argument;
		
		public In_N_C(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)(0x40 | argument.getRegister().get8BitCode() << 3) };
		}
		
	}
	
	public static class In_N_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
		
		private Expression argument;
		
		public In_N_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xDB, (byte)argument.getInteger() };
		}
		
	}
	
}
