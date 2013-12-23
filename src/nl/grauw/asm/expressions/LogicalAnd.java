package nl.grauw.asm.expressions;

public class LogicalAnd extends BinaryOperator {
	
	public LogicalAnd(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int evaluateInteger() {
		int value1 = term1.evaluateInteger();
		return value1 == 0 ? value1 : term2.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "&&";
	}
	
}
