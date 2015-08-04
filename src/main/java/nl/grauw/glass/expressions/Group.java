package nl.grauw.glass.expressions;

public class Group extends Expression {
	
	private final Expression term;
	
	public Group(Expression term) {
		this.term = term;
	}
	
	public Expression getTerm() {
		return term;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Group(term.copy(context));
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
	public boolean isString() {
		return term.isString();
	}
	
	@Override
	public String getString() {
		return term.getString();
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
	
	@Override
	public boolean isGroup() {
		return true;
	}
	
	@Override
	public boolean isContext() {
		return term.isContext();
	}
	
	@Override
	public Context getContext() {
		return term.getContext();
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
