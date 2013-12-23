package nl.grauw.asm.expressions;

public class Modulo extends BinaryOperator {
	
	public Modulo(Expression dividend, Expression divisor) {
		super(dividend, divisor);
	}
	
	public Expression getDividend() {
		return term1;
	}
	
	public Expression getDivisor() {
		return term2;
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() % term2.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "%";
	}
	
}
