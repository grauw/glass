package nl.grauw.asm.expressions;

public class Divide extends Operator {
	
	private Expression dividend;
	private Expression divisor;
	
	public Divide(Expression dividend, Expression divisor) {
		this.dividend = dividend;
		this.divisor = divisor;
	}
	
	public String toString() {
		return "" + dividend + " * " + divisor;
	}
	
}
