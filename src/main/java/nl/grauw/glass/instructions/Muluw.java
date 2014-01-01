package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Muluw extends Instruction {
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		if (Muluw_RR_RR.ARGUMENTS.check(arguments))
			return new Muluw_RR_RR(arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Muluw_RR_RR extends InstructionObject {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL, Schema.DIRECT_RR);
		
		private Expression argument;
		
		public Muluw_RR_RR(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xED, (byte)(0xC3 | argument.getRegister().get16BitCode() << 4) };
		}
		
	}
	
}
