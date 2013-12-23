package nl.grauw.asm.expressions;

public class Group extends Expression {
	
	private final Expression term;
	
	public Group(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	@Override
	public boolean isInteger() {
		return term.isInteger();
	}
	
	@Override
	public int evaluateInteger() {
		return term.evaluateInteger();
	}
	
	@Override
	public boolean isRegister() {
		return term.isRegister();
	}
	
	@Override
	public Register evaluateRegister() {
		return term.evaluateRegister();
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
