package nl.grauw.glass.expressions;

public class LessThan extends BinaryOperator {

	public LessThan(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public LessThan copy(Context context) {
		return new LessThan(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(term1.getInteger() < term2.getInteger() ? -1 : 0);
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "<";
	}

}
