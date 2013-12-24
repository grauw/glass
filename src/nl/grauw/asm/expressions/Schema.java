package nl.grauw.asm.expressions;

public class Schema {
	
	private Type[] types;
	
	public Schema(Type... types) {
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
	
	public static Type ANY = new Any();
	public static Type DIRECT = new Direct();
	public static Type INDIRECT = new Indirect();
	public static Type INTEGER = new Integer();
	public static Type DIRECT_INT = new And(DIRECT, INTEGER);
	public static Type DIRECT_A = new And(DIRECT, new Reg(Register.A));
	public static Type DIRECT_RR = new And(DIRECT, new Reg(Register.BC, Register.DE, Register.HL, Register.SP));
	public static Type DIRECT_RR_INDEX = new And(DIRECT, new Reg(Register.BC, Register.DE, Register.HL, Register.SP, Register.IX, Register.IY));
	public static Type DIRECT_DE = new And(DIRECT, new Reg(Register.DE));
	public static Type DIRECT_HL = new And(DIRECT, new Reg(Register.HL));
	public static Type DIRECT_HL_IX_IY = new And(DIRECT, new Reg(Register.HL, Register.IX, Register.IY));
	public static Type DIRECT_AF = new And(DIRECT, new Reg(Register.AF));
	public static Type DIRECT_AF_ = new And(DIRECT, new Reg(Register.AF_));
	public static Type INDIRECT_INT = new And(INDIRECT, INTEGER);
	public static Type INDIRECT_C = new And(INDIRECT, new Reg(Register.C));
	public static Type INDIRECT_SP = new And(INDIRECT, new Reg(Register.SP));
	public static Type DIRECT_R_INDIRECT_HL_IX_IY = new DirectRIndirectHLIXIY();
	
	public abstract static class Type {
		public abstract boolean check(Expression argument);
	}
	
	public static class Any extends Type {
		public boolean check(Expression argument) {
			return true;
		}
	}
	
	public static class And extends Type {
		private Type[] types;
		public And(Type... types) {
			this.types = types;
		}
		public boolean check(Expression argument) {
			for (Type type : types)
				if (!type.check(argument))
					return false;
			return true;
		}
	}
	
	public static class Direct extends Type {
		public boolean check(Expression argument) {
			return !(argument instanceof Group);
		}
	}
	
	public static class Indirect extends Type {
		public boolean check(Expression argument) {
			return argument instanceof Group;
		}
	}
	
	public static class Integer extends Type {
		public boolean check(Expression argument) {
			return argument.isInteger();
		}
	}
	
	public static class Reg extends Type {
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
	
	public static class DirectRIndirectHLIXIY extends Type {
		public boolean check(Expression argument) {
			if (argument.isRegister()) {
				Register register = argument.getRegister();
				return DIRECT.check(argument) && !register.isPair() ||
						INDIRECT.check(argument) && (register == Register.HL || register.isIndex());
			}
			return false;
		}
	}
	
}
