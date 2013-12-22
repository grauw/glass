package nl.grauw.asm.expressions;

public class Negative extends Operator {
	
	private final Expression term;
	
	public Negative(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	public String toString() {
		return "-" + term;
	}
	
	public String toDebugString() {
		return "-" + term.toDebugString();
	}
	
}
