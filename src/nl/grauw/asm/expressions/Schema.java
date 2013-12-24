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
	
	public abstract static class Type {
		public abstract boolean check(Expression argument);
	}
	
	public static Type ANY = new Any();
	public static class Any extends Type {
		public boolean check(Expression argument) {
			return true;
		}
	}
	
	public static Type DIRECT = new Direct();
	public static class Direct extends Type {
		public boolean check(Expression argument) {
			return !(argument instanceof Group);
		}
	}
	
	public static Type DIRECT_INT = new DirectInteger();
	public static class DirectInteger extends Type {
		public boolean check(Expression argument) {
			return DIRECT.check(argument) && argument.isInteger();
		}
	}
	
	public static Type DIRECT_A = new DirectRegister(Register.A);
	public static Type DIRECT_RR = new DirectRegister(Register.BC, Register.DE, Register.HL, Register.SP);
	public static Type DIRECT_RR_INDEX = new DirectRegister(Register.BC, Register.DE, Register.HL, Register.SP, Register.IX, Register.IY);
	public static Type DIRECT_DE = new DirectRegister(Register.DE);
	public static Type DIRECT_HL = new DirectRegister(Register.HL);
	public static Type DIRECT_HL_IX_IY = new DirectRegister(Register.HL, Register.IX, Register.IY);
	public static Type DIRECT_AF = new DirectRegister(Register.AF);
	public static Type DIRECT_AF_ = new DirectRegister(Register.AF_);
	public static class DirectRegister extends Type {
		private Register[] registers;
		public DirectRegister(Register... registers) {
			this.registers = registers;
		}
		public boolean check(Expression argument) {
			if (DIRECT.check(argument) && argument.isRegister()) {
				Register register = argument.getRegister();
				for (Register expected : registers)
					if (register == expected)
						return true;
			}
			return false;
		}
	}
	
	public static Type INDIRECT = new Indirect();
	public static class Indirect extends Type {
		public boolean check(Expression argument) {
			return argument instanceof Group;
		}
	}
	
	public static Type INDIRECT_INT = new IndirectInteger();
	public static class IndirectInteger extends Type {
		public boolean check(Expression argument) {
			return INDIRECT.check(argument) && argument.isInteger();
		}
	}
	
	public static Type INDIRECT_C = new IndirectC();
	public static class IndirectC extends Type {
		public boolean check(Expression argument) {
			return INDIRECT.check(argument) && argument.isRegister() && argument.getRegister() == Register.C;
		}
	}
	
	public static Type DIRECT_R_INDIRECT_HL_IX_IY = new DirectRIndirectHLIXIY();
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
	
	public static Type INDIRECT_SP = new IndirectSP();
	public static class IndirectSP extends Type {
		public boolean check(Expression argument) {
			return INDIRECT.check(argument) && argument.isRegister() && argument.getRegister() == Register.SP;
		}
	}
	
}
