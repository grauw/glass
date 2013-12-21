package nl.grauw.asm.expressions;

public class Subtract extends Operator {
	
	private Expression minuend;
	private Expression subtrahend;
	
	public Subtract(Expression minuend, Expression subtrahend) {
		this.minuend = minuend;
		this.subtrahend = subtrahend;
	}
	
	public String toString() {
		return "" + minuend + " - " + subtrahend;
	}
	
	public String toDebugString() {
		return "[" + this + "]";
	}
	
}
