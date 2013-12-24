package nl.grauw.asm.expressions;

public class Schema implements SchemaType {
	
	private SchemaType[] types;
	
	public Schema(SchemaType... types) {
		this.types = types;
	}
	
	public boolean check(Expression arguments) {
		return check(arguments, new Context() {
			public Expression getLabel(String label) {
				return new IntegerLiteral(0);
			}
			public int getAddress() {
				return 0;
			}
		});
	}
	
	public boolean check(Expression arguments, Context context) {
		for (int i = 0; arguments != null && i < types.length; i++) {
			if (!types[i].check(arguments instanceof Sequence ? ((Sequence)arguments).getValue() : arguments, context))
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
		public boolean check(Expression argument, Context context) {
			return true;
		}
	}
	
	public static class And implements SchemaType {
		private SchemaType[] types;
		public And(SchemaType... types) {
			this.types = types;
		}
		public boolean check(Expression argument, Context context) {
			for (SchemaType type : types)
				if (!type.check(argument, context))
					return false;
			return true;
		}
	}
	
	public static class Direct implements SchemaType {
		public boolean check(Expression argument, Context context) {
			return !(argument instanceof Group);
		}
	}
	
	public static class Indirect implements SchemaType {
		public boolean check(Expression argument, Context context) {
			return argument instanceof Group;
		}
	}
	
	public static class Integer implements SchemaType {
		public boolean check(Expression argument, Context context) {
			return argument.isInteger(context);
		}
	}
	
	public static class Reg implements SchemaType {
		private Register[] registers;
		public Reg(Register... registers) {
			this.registers = registers;
		}
		public boolean check(Expression argument, Context context) {
			if (argument.isRegister(context)) {
				Register register = argument.getRegister(context);
				for (Register expected : registers)
					if (register == expected)
						return true;
			}
			return false;
		}
	}
	
	public static class Register8Bit implements SchemaType {
		public boolean check(Expression argument, Context context) {
			if (argument.isRegister(context)) {
				Register register = argument.getRegister(context);
				return !register.isPair() && register != Register.I && register != Register.R;
			}
			return false;
		}
	}
	
	public static class DirectRIndirectHLIXIY implements SchemaType {
		public boolean check(Expression argument, Context context) {
			if (argument.isRegister(context)) {
				Register register = argument.getRegister(context);
				return DIRECT.check(argument, context) && !register.isPair() && register != Register.I && register != Register.R ||
						INDIRECT.check(argument, context) && (register == Register.HL || register.isIndex());
			}
			return false;
		}
	}
	
	public static class IsFlag implements SchemaType {
		public boolean check(Expression argument, Context context) {
			return argument.isFlag(context);
		}
	}
	
	public static class IsFlagZC implements SchemaType {
		public boolean check(Expression argument, Context context) {
			return argument.isFlag(context) && argument.getFlag(context).getCode() < 4;
		}
	}
	
}
