package nl.grauw.asm.expressions;

public class Group extends Expression {
	
	Expression term;
	
	public Group(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
