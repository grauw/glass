package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Ex extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("ex", this);
		scope.addInstruction("EX", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Ex_AF.ARGUMENTS.check(arguments))
			return new Ex_AF();
		if (Ex_DE_HL.ARGUMENTS.check(arguments))
			return new Ex_DE_HL();
		if (Ex_SP.ARGUMENTS.check(arguments))
			return new Ex_SP(arguments.getElement(1));
		return null;
	}
	
	public static class Ex_AF extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_AF, Schema.DIRECT_AF_);
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0x08 };
		}
		
	}
	
	public static class Ex_DE_HL extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_DE, Schema.DIRECT_HL);
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)0xEB };
		}
		
	}
	
	public static class Ex_SP extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_SP, Schema.DIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Ex_SP(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return indexifyDirect(argument.getRegister(), (byte)0xE3);
		}
		
	}
	
}
