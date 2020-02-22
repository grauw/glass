package nl.grauw.glass.expressions;

public class Divide extends BinaryOperator {

	public Divide(Expression dividend, Expression divisor) {
		super(dividend, divisor);
	}

	@Override
	public Divide copy(Context context) {
		return new Divide(term1.copy(context), term2.copy(context));
	}

	public Expression getDividend() {
		return term1;
	}

	public Expression getDivisor() {
		return term2;
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER)) {
			int divisor = term2.getInteger();
			if (divisor == 0)
				throw new EvaluationException("Division by zero.");
			return IntegerLiteral.of(term1.getInteger() / divisor);
		}
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "/";
	}

}
