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
	public int getInteger() {
		return term.getInteger();
	}
	
	@Override
	public boolean isRegister() {
		return term.isRegister();
	}
	
	@Override
	public Register getRegister() {
		return term.getRegister();
	}
	
	@Override
	public boolean isFlag() {
		return term.isFlag();
	}
	
	@Override
	public Flag getFlag() {
		return term.getFlag();
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
