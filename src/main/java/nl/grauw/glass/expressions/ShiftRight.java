package nl.grauw.glass.expressions;

public class ShiftRight extends BinaryOperator {

	public ShiftRight(Expression term1, Expression term2) {
		super(term1, term2);
	}

	@Override
	public ShiftRight copy(Context context) {
		return new ShiftRight(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(term1.getInteger() >> term2.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return ">>";
	}

}
