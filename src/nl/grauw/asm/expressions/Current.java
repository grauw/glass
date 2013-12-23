package nl.grauw.asm.expressions;

public class Current extends Expression {
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Currently not supported.");
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	public String toString() {
		return "$";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
