package nl.grauw.asm.expressions;

public class Subtract extends BinaryOperator {
	
	public Subtract(Expression minuend, Expression subtrahend) {
		super(minuend, subtrahend);
	}
	
	public Expression getMinuend() {
		return term1;
	}
	
	public Expression getSubtrahend() {
		return term2;
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() - term2.evaluateInteger();
	}
	
	public String toString() {
		return "" + term1 + " - " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " - " + term2.toDebugString() + "}";
	}
	
}
