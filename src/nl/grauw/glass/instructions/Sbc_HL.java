package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Sbc_HL extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);
	
	private Expression argument;
	
	public Sbc_HL(Expression argument) {
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
	
	public static class Factory extends InstructionFactory {
		
		@Override
		public void register(Scope scope) {
			scope.addInstruction("sbc", this);
			scope.addInstruction("SBC", this);
		}
		
		@Override
		public Instruction createInstruction(Expression arguments) {
			if (Sbc_HL.ARGUMENTS.check(arguments))
				return new Sbc_HL(arguments.getElement(1));
			return null;
		}
		
	}
	
}
