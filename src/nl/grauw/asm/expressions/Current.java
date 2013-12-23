package nl.grauw.asm.expressions;

public class Current extends Expression {
	
	@Override
	public int evaluateInteger() {
		throw new EvaluationException("Currently not supported.");
	}
	
	public String toString() {
		return "$";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
