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
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(+term.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "+";
	}

}
