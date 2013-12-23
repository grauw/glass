package nl.grauw.asm.expressions;

public class GreaterThan extends BinaryOperator {
	
	public GreaterThan(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() > term2.evaluateInteger() ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return ">";
	}
	
}
