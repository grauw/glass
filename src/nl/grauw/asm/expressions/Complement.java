package nl.grauw.asm.expressions;

public class Complement extends Operator {
	
	private final Expression term;
	
	public Complement(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	public String toString() {
		return "~" + term;
	}
	
	public String toDebugString() {
		return "~" + term.toDebugString();
	}
	
}
