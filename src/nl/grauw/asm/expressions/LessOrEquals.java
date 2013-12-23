package nl.grauw.asm.expressions;

public class LessOrEquals extends BinaryOperator {
	
	public LessOrEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() <= term2.evaluateInteger() ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return "<=";
	}
	
}
