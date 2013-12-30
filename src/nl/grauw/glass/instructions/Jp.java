package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Jp extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("jp", this);
		scope.addInstruction("JP", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Jp_F_N.ARGUMENTS.check(arguments))
			return new Jp_F_N(arguments.getElement(0), arguments.getElement(1));
		if (Jp_HL.ARGUMENTS.check(arguments) || Jp_HL.ARGUMENTS_ALT.check(arguments))
			return new Jp_HL(arguments.getElement(0));
		if (Jp_N.ARGUMENTS.check(arguments))
			return new Jp_N(arguments.getElement(0));
		return null;
	}
	
	public static class Jp_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_N);
		
		private Expression argument;
		
		public Jp_N(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 3;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument.getAddress();
			return new byte[] { (byte)0xC3, (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Jp_F_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(new Schema.IsFlag(), Schema.DIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Jp_F_N(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return 3;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument2.getAddress();
			return new byte[] { (byte)(0xC2 | argument1.getFlag().getCode() << 3), (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Jp_HL extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_HL_IX_IY);
		public static Schema ARGUMENTS_ALT = new Schema(Schema.DIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Jp_HL(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return indexifyDirect(argument.getRegister(), (byte)0xE9);
		}
		
	}
	
}
