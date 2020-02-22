package nl.grauw.glass.expressions;

public class Add extends BinaryOperator {

	public Add(Expression augend, Expression addend) {
		super(augend, addend);
	}

	@Override
	public Add copy(Context context) {
		return new Add(term1.copy(context), term2.copy(context));
	}

	public Expression getAugend() {
		return term1;
	}

	public Expression getAddend() {
		return term2;
	}

	@Override
	public boolean is(Expression type) {
		if (type.is(Type.REGISTER)) {
			if (term1.is(Type.REGISTER)) {
				Register register = term1.getRegister();
				return register.isIndex() && register.isPair();
			}
			return false;
		}
		return super.is(type);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(term1.getInteger() + term2.getInteger());
		if (type.is(Type.REGISTER)) {
			if (term1.is(Type.REGISTER)) {
				Register register = term1.getRegister();
				if (register.isIndex() && register.isPair()) {
					return new Register(register, new Add(register.getIndexOffset(), term2));
				}
			}
		}
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "+";
	}

}
