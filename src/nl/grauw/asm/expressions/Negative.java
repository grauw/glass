package nl.grauw.asm.expressions;

public class Negative extends Operator {
	
	private Expression term;
	
	public Negative(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "-" + term;
	}
	
	public String toDebugString() {
		return "-" + term.toDebugString();
	}
	
}
