package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Context;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Out_C extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_C, Schema.DIRECT_R);
	
	private Expression argument;
	
	public Out_C(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xED, (byte)(0x41 | argument.getRegister().get8BitCode() << 3) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("out", this);
			scope.addInstruction("OUT", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new Out_C(arguments.getElement(1));
			return null;
		}
		
	}
	
}