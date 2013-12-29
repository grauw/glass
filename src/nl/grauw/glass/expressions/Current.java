package nl.grauw.glass.expressions;

public class Current extends ContextLiteral {
	
	public Current(Context context) {
		super(context);
	}
	
	@Override
	public Expression copy(Context context) {
		return new Current(context);
	}
	
	public String toString() {
		return "$";
	}
	
}
