package nl.grauw.asm.expressions;

public class Positive extends UnaryOperator {
	
	private final Expression term;
	
	public Positive(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	@Override
	public int evaluateInteger() {
		return +term.evaluateInteger();
	}
	
	public String toString() {
		return "+" + term;
	}
	
	public String toDebugString() {
		return "+" + term.toDebugString();
	}
	
}
