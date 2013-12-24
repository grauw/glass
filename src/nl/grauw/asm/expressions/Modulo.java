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
	public int getInteger(Context context) {
		return term1.getInteger(context) % term2.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "%";
	}
	
}
