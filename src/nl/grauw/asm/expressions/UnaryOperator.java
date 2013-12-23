package nl.grauw.asm.expressions;

public abstract class UnaryOperator extends Operator {
	
	protected final Expression term;
	
	public abstract String getSymbol();
	
	public UnaryOperator(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	@Override
	public boolean isInteger() {
		return term.isInteger();
	}
	
	public String toString() {
		return getSymbol() + term;
	}
	
	public String toDebugString() {
		return getSymbol() + term.toDebugString();
	}
	
}
