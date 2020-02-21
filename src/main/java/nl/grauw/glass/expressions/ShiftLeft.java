package nl.grauw.glass.expressions;

public class ShiftLeft extends BinaryOperator {

	public ShiftLeft(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public ShiftLeft copy(Context context) {
		return new ShiftLeft(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(term1.getInteger() << term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "<<";
	}

}
