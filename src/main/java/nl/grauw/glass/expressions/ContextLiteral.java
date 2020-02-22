package nl.grauw.glass.expressions;

public class ContextLiteral extends Expression {

	private final Context context;

	public ContextLiteral(Context context) {
		this.context = context;
	}

	@Override
	public ContextLiteral copy(Context context) {
		return new ContextLiteral(context);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.INTEGER) || type.is(Type.CONTEXT);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.CONTEXT))
			return this;
		if (type.is(Type.INTEGER))
			return context.getAddress();
		return super.get(type);
	}

	public String toString() {
		return "$";
	}

	public String toDebugString() {
		return toString();
	}

}
