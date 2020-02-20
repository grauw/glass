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
	public int getInteger() {
		return context.getAddress();
	}

	public String toString() {
		return "$";
	}

	public String toDebugString() {
		return toString();
	}

}
