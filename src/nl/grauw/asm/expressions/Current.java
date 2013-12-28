package nl.grauw.asm.expressions;

public class Current extends Expression {
	
	private Context context;
	
	public Current(Context context) {
		this.context = context;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Current(context);
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
		return "$";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
