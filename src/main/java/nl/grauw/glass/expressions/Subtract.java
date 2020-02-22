package nl.grauw.glass.expressions;

public class Subtract extends BinaryOperator {

	public Subtract(Expression minuend, Expression subtrahend) {
		super(minuend, subtrahend);
	}

	@Override
	public Subtract copy(Context context) {
		return new Subtract(term1.copy(context), term2.copy(context));
	}

	public Expression getMinuend() {
		return term1;
	}

	public Expression getSubtrahend() {
		return term2;
	}

	@Override
	public boolean is(Expression type) {
		if (type.is(Type.REGISTER)) {
			if (term1.is(Type.REGISTER) && term2.is(Type.INTEGER)) {
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
			return IntegerLiteral.of(term1.getInteger() - term2.getInteger());
		if (type.is(Type.REGISTER)) {
			if (term1.is(Type.REGISTER) && term2.is(Type.INTEGER)) {
				Register register = term1.getRegister();
				if (register.isIndex() && register.isPair()) {
					return new Register(register, new Subtract(register.getIndexOffset(), term2));
				}
			}
		}
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "-";
	}

}
