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
	
	public String toString() {
		return "" + term1 + " && " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " && " + term2.toDebugString() + "}";
	}
	
}
