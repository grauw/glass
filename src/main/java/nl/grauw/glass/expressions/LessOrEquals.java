package nl.grauw.glass.expressions;

public class LessOrEquals extends BinaryOperator {

	public LessOrEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public LessOrEquals copy(Context context) {
		return new LessOrEquals(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(term1.getInteger() <= term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "<=";
	}

}
