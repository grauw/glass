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
	
	public static Type DIRECT_INT = new DirectInteger();
	public static class DirectInteger extends Type {
		public boolean check(Expression argument) {
			return !(argument instanceof Group) && argument.isInteger();
		}
	}
	
	public static Type DIRECT_A = new DirectA();
	public static class DirectA extends Type {
		public boolean check(Expression argument) {
			return !(argument instanceof Group) && argument.isRegister() && argument.getRegister() == Register.A;
		}
	}
	
	public static Type DIRECT_HL = new DirectHL();
	public static class DirectHL extends Type {
		public boolean check(Expression argument) {
			return !(argument instanceof Group) && argument.isRegister() && argument.getRegister() == Register.HL;
		}
	}
	
	public static Type DIRECT_HL_IX_IY = new DirectHLIXIY();
	public static class DirectHLIXIY extends Type {
		public boolean check(Expression argument) {
			if (!(argument instanceof Group) && argument.isRegister()) {
				Register register = argument.getRegister();
				return register == Register.HL || register == Register.IX || register == Register.IY;
			}
			return false;
		}
	}
	
	public static Type INDIRECT_INT = new IndirectInteger();
	public static class IndirectInteger extends Type {
		public boolean check(Expression argument) {
			return argument instanceof Group && argument.isInteger();
		}
	}
	
	public static Type INDIRECT_C = new IndirectC();
	public static class IndirectC extends Type {
		public boolean check(Expression argument) {
			return argument instanceof Group && argument.isRegister() && argument.getRegister() == Register.C;
		}
	}
	
	public static Type DIRECT_R_INDIRECT_HL_IX_IY = new DirectRIndirectHLIXIY();
	public static class DirectRIndirectHLIXIY extends Type {
		public boolean check(Expression argument) {
			if (argument.isRegister()) {
				Register register = argument.getRegister();
				return !(argument instanceof Group) && !register.isPair() ||
						argument instanceof Group && (register == Register.HL || register.isIndex());
			}
			return false;
		}
	}
	
}
