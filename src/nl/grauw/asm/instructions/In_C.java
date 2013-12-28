package nl.grauw.asm.instructions;

import nl.grauw.asm.Scope;
import nl.grauw.asm.expressions.Context;
import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Register;
import nl.grauw.asm.expressions.Schema;

public class In_C extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R, Schema.INDIRECT_C);
	public static Schema ARGUMENTS_NO_R = new Schema(Schema.INDIRECT_C);
	
	private Expression argument;
	
	public In_C(Expression arguments) {
		this.argument = arguments;
	}
	
	@Override
	public int getSize(Context context) {
		return 2;
	}
	
	@Override
	public byte[] getBytes(Context context) {
		return new byte[] { (byte)0xED, (byte)(0x40 | argument.getRegister().get8BitCode() << 3) };
	}
	
	public static class Factory implements InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("in", this);
			scope.addInstruction("IN", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (ARGUMENTS.check(arguments))
				return new In_C(arguments.getElement(0));
			if (ARGUMENTS_NO_R.check(arguments))
				return new In_C(Register.HL);
			return null;
		}
		
	}
	
}
