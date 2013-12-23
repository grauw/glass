package nl.grauw.asm.expressions;

public abstract class UnaryOperator extends Operator {
	
	protected final Expression term;
	
	public UnaryOperator(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	public abstract String getSymbol();
	
	public String toString() {
		return getSymbol() + term;
	}
	
	public String toDebugString() {
		return getSymbol() + term.toDebugString();
	}
	
}
