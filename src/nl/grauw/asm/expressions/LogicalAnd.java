package nl.grauw.asm.expressions;

public class LogicalAnd extends Operator {
	
	private final Expression term1;
	private final Expression term2;
	
	public LogicalAnd(Expression term1, Expression term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public Expression getTerm1() {
		return term1;
	}
	
	public Expression getTerm2() {
		return term2;
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
