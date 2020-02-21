package nl.grauw.glass.expressions;

public class And extends BinaryOperator {

	public And(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public And copy(Context context) {
		return new And(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(term1.getInteger() & term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "&";
	}

}
