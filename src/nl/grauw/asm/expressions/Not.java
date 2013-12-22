package nl.grauw.asm.expressions;

public class Not extends Operator {
	
	private final Expression term;
	
	public Not(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	public String toString() {
		return "!" + term;
	}
	
	public String toDebugString() {
		return "!" + term.toDebugString();
	}
	
}
