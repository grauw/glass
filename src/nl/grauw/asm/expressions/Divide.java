package nl.grauw.asm.expressions;

public class Divide extends BinaryOperator {
	
	public Divide(Expression dividend, Expression divisor) {
		super(dividend, divisor);
	}
	
	public Expression getDividend() {
		return term1;
	}
	
	public Expression getDivisor() {
		return term2;
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() / term2.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "/";
	}
	
}
