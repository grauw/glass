package nl.grauw.asm.expressions;

public abstract class BinaryOperator extends Operator {
	
	protected final Expression term1;
	protected final Expression term2;
	
	public BinaryOperator(Expression term1, Expression term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public Expression getTerm1() {
		return term1;
	}
	
	public Expression getTerm2() {
		return term2;
	}
	
}
