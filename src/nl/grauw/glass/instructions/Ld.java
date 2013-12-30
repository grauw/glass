package nl.grauw.glass.instructions;

import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Register;
import nl.grauw.glass.expressions.Schema;

public class Ld extends InstructionFactory {
	
	@Override
	public void register(Scope scope) {
		scope.addInstruction("ld", this);
		scope.addInstruction("LD", this);
	}
	
	@Override
	public Instruction createInstruction(Expression arguments) {
		if (Ld_R_R.ARGUMENTS.check(arguments))
			return new Ld_R_R(arguments.getElement(0).getRegister(), arguments.getElement(1).getRegister());
		if (Ld_A_BCDE.ARGUMENTS.check(arguments))
			return new Ld_A_BCDE(arguments.getElement(1));
		if (Ld_BCDE_A.ARGUMENTS.check(arguments))
			return new Ld_BCDE_A(arguments.getElement(0));
		if (Ld_SP_HL.ARGUMENTS.check(arguments))
			return new Ld_SP_HL(arguments.getElement(1));
		if (Ld_A_IR.ARGUMENTS.check(arguments))
			return new Ld_A_IR(arguments.getElement(1));
		if (Ld_IR_A.ARGUMENTS.check(arguments))
			return new Ld_IR_A(arguments.getElement(0));
		if (Ld_R_N.ARGUMENTS.check(arguments))
			return new Ld_R_N(arguments.getElement(0), arguments.getElement(1));
		if (Ld_RR_N.ARGUMENTS.check(arguments))
			return new Ld_RR_N(arguments.getElement(0), arguments.getElement(1));
		if (Ld_A_NN.ARGUMENTS.check(arguments))
			return new Ld_A_NN(arguments.getElement(0));
		if (Ld_HL_NN.ARGUMENTS.check(arguments))
			return new Ld_HL_NN(arguments.getElement(0), arguments.getElement(1));
		if (Ld_RR_NN.ARGUMENTS.check(arguments))
			return new Ld_RR_NN(arguments.getElement(0), arguments.getElement(1));
		if (Ld_NN_A.ARGUMENTS.check(arguments))
			return new Ld_NN_A(arguments.getElement(1));
		if (Ld_NN_HL.ARGUMENTS.check(arguments))
			return new Ld_NN_HL(arguments.getElement(0), arguments.getElement(1));
		if (Ld_NN_RR.ARGUMENTS.check(arguments))
			return new Ld_NN_RR(arguments.getElement(0), arguments.getElement(1));
		throw new ArgumentException();
	}
	
	public static class Ld_R_R extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY, Schema.DIRECT_R_INDIRECT_HL_IX_IY);
		
		private Register register1;
		private Register register2;
		
		public Ld_R_R(Register register1, Register register2) {
			this.register1 = register1;
			this.register2 = register2;
			
			if (register1.isPair() && register2.isPair())
				throw new ArgumentException();  // forbid (hl),(hl), (ix+0),(ix-0), etc.
			if (register1.isIndex() && register2.isIndex() && register1.getIndexCode() != register2.getIndexCode())
				throw new ArgumentException();  // forbid ixh,iyl, etc.
			if (register1.isIndex() && register2.isPair() || register1.isPair() && register2.isIndex())
				throw new ArgumentException();  // forbid (hl),ixh, ixh,(ix+0)
			if (register1.isIndex() && !register1.isPair() && (register2 == Register.H || register2 == Register.L))
				throw new ArgumentException();  // forbid iyl,h
			if (register2.isIndex() && !register2.isPair() && (register1 == Register.H || register1 == Register.L))
				throw new ArgumentException();  // forbid h,iyl
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(register1.isIndex() ? register1 : register2, 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return indexifyIndirect(register1.isIndex() ? register1 : register2,
					(byte)(0x40 | register1.get8BitCode() << 3 | register2.get8BitCode()));
		}
		
	}
	
	public static class Ld_A_BCDE extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_BC_DE);
		
		private Expression argument;
		
		public Ld_A_BCDE(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)(0x0A | argument.getRegister().get16BitCode() << 4) };
		}
		
	}
	
	public static class Ld_BCDE_A extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_BC_DE, Schema.DIRECT_A);
		
		private Expression argument;
		
		public Ld_BCDE_A(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 1;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return new byte[] { (byte)(0x02 | argument.getRegister().get16BitCode() << 4) };
		}
		
	}
	
	public static class Ld_SP_HL extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_SP, Schema.DIRECT_HL_IX_IY);
		
		private Expression argument;
		
		public Ld_SP_HL(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument.getRegister(), 1);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			return indexifyDirect(argument.getRegister(), (byte)0xF9);
		}
		
	}
	
	public static class Ld_A_IR extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.DIRECT_IR);
		
		private Expression argument;
		
		public Ld_A_IR(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			if (argument.getRegister() == Register.I)
				return new byte[] { (byte)0xED, (byte)0x57 };
			return new byte[] { (byte)0xED, (byte)0x5F };
		}
		
	}
	
	public static class Ld_IR_A extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_IR, Schema.DIRECT_A);
		
		private Expression argument;
		
		public Ld_IR_A(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 2;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			if (argument.getRegister() == Register.I)
				return new byte[] { (byte)0xED, (byte)0x47 };
			return new byte[] { (byte)0xED, (byte)0x4F };
		}
		
	}
	
	public static class Ld_R_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_R_INDIRECT_HL_IX_IY, Schema.DIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_R_N(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyIndirect(argument1.getRegister(), 2);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument1.getRegister();
			return indexifyIndirect(register, (byte)(0x06 | register.get8BitCode() << 3), (byte)argument2.getInteger());
		}
		
	}
	
	public static class Ld_RR_N extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR_INDEX, Schema.DIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_RR_N(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument1.getRegister(), 3);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			Register register = argument1.getRegister();
			return indexifyDirect(register, (byte)(0x01 | register.get16BitCode() << 4),
					(byte)argument2.getInteger(), (byte)(argument2.getInteger() >> 8));
		}
		
	}
	
	public static class Ld_A_NN extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_A);
		
		private Expression argument;
		
		public Ld_A_NN(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 3;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument.getAddress();
			return new byte[] { (byte)0x32, (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Ld_HL_NN extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_HL_IX_IY, Schema.INDIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_HL_NN(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument1.getRegister(), 3);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument2.getAddress();
			return indexifyDirect(argument1.getRegister(), (byte)0x2A, (byte)address, (byte)(address >> 8));
		}
		
	}
	
	public static class Ld_RR_NN extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_RR, Schema.INDIRECT_N);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_RR_NN(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return 4;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument2.getAddress();
			return new byte[] { (byte)0xED, (byte)(0x4B | argument1.getRegister().get16BitCode() << 4),
					(byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Ld_NN_A extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.DIRECT_A, Schema.INDIRECT_N);
		
		private Expression argument;
		
		public Ld_NN_A(Expression argument) {
			this.argument = argument;
		}
		
		@Override
		public int getSize(Scope context) {
			return 3;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument.getAddress();
			return new byte[] { (byte)0x3A, (byte)address, (byte)(address >> 8) };
		}
		
	}
	
	public static class Ld_NN_HL extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_HL_IX_IY);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_NN_HL(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return indexifyDirect(argument2.getRegister(), 3);
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument1.getAddress();
			return indexifyDirect(argument2.getRegister(), (byte)0x22, (byte)address, (byte)(address >> 8));
		}
		
	}
	
	public static class Ld_NN_RR extends Instruction {
		
		public static Schema ARGUMENTS = new Schema(Schema.INDIRECT_N, Schema.DIRECT_RR);
		
		private Expression argument1;
		private Expression argument2;
		
		public Ld_NN_RR(Expression argument1, Expression argument2) {
			this.argument1 = argument1;
			this.argument2 = argument2;
		}
		
		@Override
		public int getSize(Scope context) {
			return 4;
		}
		
		@Override
		public byte[] getBytes(Scope context) {
			int address = argument1.getAddress();
			return new byte[] { (byte)0xED, (byte)(0x43 | argument2.getRegister().get16BitCode() << 4),
					(byte)address, (byte)(address >> 8) };
		}
		
	}
	
}
