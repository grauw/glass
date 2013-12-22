package nl.grauw.asm.expressions;

public class Subtract extends Operator {
	
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
	
	public String toString() {
		return "" + minuend + " - " + subtrahend;
	}
	
	public String toDebugString() {
		return "{" + minuend.toDebugString() + " - " + subtrahend.toDebugString() + "}";
	}
	
}
