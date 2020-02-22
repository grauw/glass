package nl.grauw.glass.expressions;

public class Positive extends UnaryOperator {

	public Positive(Expression term) {
		super(term);
	}

	@Override
	public Positive copy(Context context) {
		return new Positive(term.copy(context));
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(+term.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "+";
	}

}
