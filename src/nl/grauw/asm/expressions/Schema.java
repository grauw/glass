package nl.grauw.asm.expressions;

public class Schema implements SchemaType {
	
	private SchemaType[] types;
	
	public Schema(SchemaType... types) {
		this.types = types;
	}
	
	public boolean check(Expression arguments) {
		for (int i = 0; arguments != null && i < types.length; i++) {
			if (!types[i].check(arguments instanceof Sequence ? ((Sequence)arguments).getValue() : arguments))
				return false;
			arguments = arguments instanceof Sequence ? ((Sequence)arguments).getTail() : null;
		}
		return arguments == null;
	}
	
	public static SchemaType ANY = new Any();
	public static SchemaType DIRECT = new Direct();
	public static SchemaType INDIRECT = new Indirect();
	public static SchemaType INTEGER = new Integer();
	public static SchemaType DIRECT_N = new And(DIRECT, INTEGER);
	public static SchemaType DIRECT_R = new And(DIRECT, new Register8Bit());
	public static SchemaType DIRECT_A = new And(DIRECT, new Reg(Register.A));
	public static SchemaType DIRECT_IR = new And(DIRECT, new Reg(Register.I, Register.R));
	public static SchemaType DIRECT_RR = new And(DIRECT, new Reg(Register.BC, Register.DE, Register.HL, Register.SP));
	public static SchemaType DIRECT_RR_INDEX = new And(DIRECT, new Reg(Register.BC, Register.DE, Register.HL, Register.SP, Register.IX, Register.IY));
	public static SchemaType DIRECT_RR_AF_INDEX = new And(DIRECT, new Reg(Register.BC, Register.DE, Register.HL, Register.AF, Register.IX, Register.IY));
	public static SchemaType DIRECT_DE = new And(DIRECT, new Reg(Register.DE));
	public static SchemaType DIRECT_HL = new And(DIRECT, new Reg(Register.HL));
	public static SchemaType DIRECT_HL_IX_IY = new And(DIRECT, new Reg(Register.HL, Register.IX, Register.IY));
	public static SchemaType DIRECT_SP = new And(DIRECT, new Reg(Register.SP));
	public static SchemaType DIRECT_AF = new And(DIRECT, new Reg(Register.AF));
	public static SchemaType DIRECT_AF_ = new And(DIRECT, new Reg(Register.AF_));
	public static SchemaType INDIRECT_N = new And(INDIRECT, INTEGER);
	public static SchemaType INDIRECT_C = new And(INDIRECT, new Reg(Register.C));
	public static SchemaType INDIRECT_BC_DE = new And(INDIRECT, new Reg(Register.BC, Register.DE));
	public static SchemaType INDIRECT_HL_IX_IY = new And(INDIRECT, new Reg(Register.HL, Register.IX, Register.IY));
	public static SchemaType INDIRECT_SP = new And(INDIRECT, new Reg(Register.SP));
	public static SchemaType DIRECT_R_INDIRECT_HL_IX_IY = new DirectRIndirectHLIXIY();
	
	public static class Any implements SchemaType {
		public boolean check(Expression argument) {
			return true;
		}
	}
	
	public static class And implements SchemaType {
		private SchemaType[] types;
		public And(SchemaType... types) {
			this.types = types;
		}
		public boolean check(Expression argument) {
			for (SchemaType type : types)
				if (!type.check(argument))
					return false;
			return true;
		}
	}
	
	public static class Direct implements SchemaType {
		public boolean check(Expression argument) {
			return !(argument instanceof Group);
		}
	}
	
	public static class Indirect implements SchemaType {
		public boolean check(Expression argument) {
			return argument instanceof Group;
		}
	}
	
	public static class Integer implements SchemaType {
		public boolean check(Expression argument) {
			return argument.isInteger();
		}
	}
	
	public static class Reg implements SchemaType {
		private Register[] registers;
		public Reg(Register... registers) {
			this.registers = registers;
		}
		public boolean check(Expression argument) {
			if (argument.isRegister()) {
				Register register = argument.getRegister();
				for (Register expected : registers)
					if (register == expected)
						return true;
			}
			return false;
		}
	}
	
	public static class Register8Bit implements SchemaType {
		public boolean check(Expression argument) {
			if (argument.isRegister()) {
				Register register = argument.getRegister();
				return !register.isPair() && register != Register.I && register != Register.R;
			}
			return false;
		}
	}
	
	public static class DirectRIndirectHLIXIY implements SchemaType {
		public boolean check(Expression argument) {
			if (argument.isRegister()) {
				Register register = argument.getRegister();
				return DIRECT.check(argument) && !register.isPair() && register != Register.I && register != Register.R ||
						INDIRECT.check(argument) && (register == Register.HL || register.isIndex());
			}
			return false;
		}
	}
	
	public static class IsFlag implements SchemaType {
		public boolean check(Expression argument) {
			return argument.isFlag();
		}
	}
	
	public static class IsFlagZC implements SchemaType {
		public boolean check(Expression argument) {
			return argument.isFlag() && argument.getFlag().getCode() < 4;
		}
	}
	
}
