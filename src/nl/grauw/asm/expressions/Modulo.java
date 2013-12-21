package nl.grauw.asm.expressions;

public class Modulo extends Operator {
	
	private Expression dividend;
	private Expression divisor;
	
	public Modulo(Expression dividend, Expression divisor) {
		this.dividend = dividend;
		this.divisor = divisor;
	}
	
	public String toString() {
		return "" + dividend + " % " + divisor;
	}
	
	public String toDebugString() {
		return "{" + dividend.toDebugString() + " % " + divisor.toDebugString() + "}";
	}
	
}
