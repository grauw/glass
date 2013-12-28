package nl.grauw.glass.expressions;

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
	public Expression copy(Context context) {
		return new Modulo(term1.copy(context), term2.copy(context));
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
