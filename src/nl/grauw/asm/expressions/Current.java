package nl.grauw.asm.expressions;

public class Current extends Expression {
	
	@Override
	public boolean isInteger(Context context) {
		return true;
	}
	
	@Override
	public int getInteger(Context context) {
		return context.getAddress();
	}
	
	public String toString() {
		return "$";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
