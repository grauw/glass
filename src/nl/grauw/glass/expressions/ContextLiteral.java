package nl.grauw.glass.expressions;

public class ContextLiteral extends Literal {
	
	private final Context context;
	
	public ContextLiteral(Context context) {
		this.context = context;
	}
	
	public Context getContext() {
		return context;
	}
	
	@Override
	public Expression copy(Context context) {
		return new ContextLiteral(context);
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	@Override
	public int getInteger() {
		return context.getAddress();
	}
	
	public String toString() {
		try {
			String string = Integer.toHexString(getInteger()).toUpperCase();
			return (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
		} catch (EvaluationException e) {
			return "<" + e.getMessage() + ">";
		}
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
