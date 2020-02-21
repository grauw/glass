package nl.grauw.glass.expressions;

public class Or extends BinaryOperator {

	public Or(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public Or copy(Context context) {
		return new Or(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(term1.getInteger() | term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "|";
	}

}
