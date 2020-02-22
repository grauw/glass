package nl.grauw.glass.expressions;

public class FlagOrRegister extends Expression {

	public static FlagOrRegister C = new FlagOrRegister(Flag.C, Register.C);

	private final Flag flag;
	private final Register register;

	public FlagOrRegister(Flag flag, Register register) {
		this.flag = flag;
		this.register = register;
	}

	@Override
	public FlagOrRegister copy(Context context) {
		return this;
	}

	@Override
	public Flag getFlag() {
		return flag;
	}

	@Override
	public Register getRegister() {
		return register;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.FLAG) || type.is(Type.REGISTER);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.FLAG))
			return flag;
		if (type.is(Type.REGISTER))
			return register;
		return super.get(type);
	}

	@Override
	public String toString() {
		return flag.toString();
	}

	@Override
	public String toDebugString() {
		return flag.toString();
	}

	public static Expression getByName(String name) {
		Flag flag = Flag.getByName(name);
		Register register = Register.getByName(name);
		if (flag != null && register == null)
			return flag;
		if (flag == null && register != null)
			return register;
		if (flag != null && register != null)
			return new FlagOrRegister(flag, register);
		return null;
	}

}
