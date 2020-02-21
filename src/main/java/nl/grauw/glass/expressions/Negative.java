package nl.grauw.glass.expressions;

public class Negative extends UnaryOperator {

	public Negative(Expression term) {
		super(term);
	}

	@Override
	public Negative copy(Context context) {
		return new Negative(term.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(-term.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "-";
	}

}
