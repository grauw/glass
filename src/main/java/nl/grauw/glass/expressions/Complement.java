package nl.grauw.glass.expressions;

public class Complement extends UnaryOperator {

	public Complement(Expression term) {
		super(term);
	}

	@Override
	public Complement copy(Context context) {
		return new Complement(term.copy(context));
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return new IntegerLiteral(~term.getInteger());
		return super.get(type);
	}

	@Override
	public String getLexeme() {
		return "~";
	}

}
