package nl.grauw.glass.expressions;

public class GreaterOrEquals extends BinaryOperator {

	public GreaterOrEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public GreaterOrEquals copy(Context context) {
		return new GreaterOrEquals(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(term1.getInteger() >= term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return ">=";
	}

}
