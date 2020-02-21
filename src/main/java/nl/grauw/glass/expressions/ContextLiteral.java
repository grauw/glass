package nl.grauw.glass.expressions;

public class ContextLiteral extends Literal {

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
	public boolean is(Type type) {
		return type == Type.INTEGER || type == Type.CONTEXT;
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.CONTEXT)
			return this;
		if (type == Type.INTEGER)
			return new IntegerLiteral(context.getAddress());
		return super.get(type);
	}

	public String toString() {
		return "$";
	}

	public String toDebugString() {
		return toString();
	}

}
