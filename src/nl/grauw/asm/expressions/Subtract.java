package nl.grauw.asm.expressions;

public class Subtract extends BinaryOperator {
	
	private final Expression minuend;
	private final Expression subtrahend;
	
	public Subtract(Expression minuend, Expression subtrahend) {
		this.minuend = minuend;
		this.subtrahend = subtrahend;
	}
	
	public Expression getMinuend() {
		return minuend;
	}
	
	public Expression getSubtrahend() {
		return subtrahend;
	}
	
	@Override
	public int evaluateInteger() {
		return minuend.evaluateInteger() - subtrahend.evaluateInteger();
	}
	
	public String toString() {
		return "" + minuend + " - " + subtrahend;
	}
	
	public String toDebugString() {
		return "{" + minuend.toDebugString() + " - " + subtrahend.toDebugString() + "}";
	}
	
}
