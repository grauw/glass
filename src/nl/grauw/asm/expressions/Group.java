package nl.grauw.asm.expressions;

public class Group extends Expression {
	
	private final Expression term;
	
	public Group(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	@Override
	public int evaluateInteger() {
		return term.evaluateInteger();
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
