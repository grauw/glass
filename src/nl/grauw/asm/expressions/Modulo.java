package nl.grauw.asm.expressions;

public class Modulo extends Operator {
	
	private final Expression dividend;
	private final Expression divisor;
	
	public Modulo(Expression dividend, Expression divisor) {
		this.dividend = dividend;
		this.divisor = divisor;
	}
	
	public Expression getDividend() {
		return dividend;
	}
	
	public Expression getDivisor() {
		return divisor;
	}
	
	@Override
	public int evaluateInteger() {
		return dividend.evaluateInteger() % divisor.evaluateInteger();
	}
	
	public String toString() {
		return "" + dividend + " % " + divisor;
	}
	
	public String toDebugString() {
		return "{" + dividend.toDebugString() + " % " + divisor.toDebugString() + "}";
	}
	
}
