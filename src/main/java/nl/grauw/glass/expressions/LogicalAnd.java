package nl.grauw.glass.expressions;

public class LogicalAnd extends BinaryOperator {

	public LogicalAnd(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public LogicalAnd copy(Context context) {
		return new LogicalAnd(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER) {
			int value1 = term1.getInteger();
			return new IntegerLiteral(value1 == 0 ? value1 : term2.getInteger());
		}
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "&&";
	}

}
