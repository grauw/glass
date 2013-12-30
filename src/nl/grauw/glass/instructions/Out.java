package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Out extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("out", this);
		scope.addInstruction("OUT", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Out_C_N.ARGUMENTS.check(arguments))
			return new Out_C_N(arguments.getElement(1));
		if (Out_N_N.ARGUMENTS.check(arguments))
			return new Out_N_N(arguments.getElement(0));
		return null;
	}
	
	public static class Out_C_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_C, Schema.DIRECT_R);
		
		private Expression argument;
		
		public Out_C_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)(0x41 | argument.getRegister().get8BitCode() << 3) };
		}
		
	}
	
	public static class Out_N_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
		
		private Expression argument;
		
		public Out_N_N(Expression arguments) {
			this.argument = arguments;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xD3, (byte)argument.getInteger() };
		}
		
	}
	
}
