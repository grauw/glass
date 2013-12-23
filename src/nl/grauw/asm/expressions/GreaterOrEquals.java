package nl.grauw.asm.expressions;

public class GreaterOrEquals extends Operator {
	
	private final Expression term1;
	private final Expression term2;
	
	public GreaterOrEquals(Expression term1, Expression term2) {
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
		return term1.evaluateInteger() >= term2.evaluateInteger() ? -1 : 0;
	}
	
	public String toString() {
		return "" + term1 + " >= " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " >= " + term2.toDebugString() + "}";
	}
	
}
