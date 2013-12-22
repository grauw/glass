package nl.grauw.asm.expressions;

public class Divide extends Operator {
	
	private final Expression dividend;
	private final Expression divisor;
	
	public Divide(Expression dividend, Expression divisor) {
		this.dividend = dividend;
		this.divisor = divisor;
	}
	
	public Expression getDividend() {
		return dividend;
	}
	
	public Expression getDivisor() {
		return divisor;
	}
	
	public String toString() {
		return "" + dividend + " * " + divisor;
	}
	
	public String toDebugString() {
		return "{" + dividend.toDebugString() + " * " + divisor.toDebugString() + "}";
	}
	
}
