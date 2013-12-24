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
	public boolean isInteger(Context context) {
		return term.isInteger(context);
	}
	
	@Override
	public int getInteger(Context context) {
		return term.getInteger(context);
	}
	
	@Override
	public boolean isRegister(Context context) {
		return term.isRegister(context);
	}
	
	@Override
	public Register getRegister(Context context) {
		return term.getRegister(context);
	}
	
	@Override
	public boolean isFlag(Context context) {
		return term.isFlag(context);
	}
	
	@Override
	public Flag getFlag(Context context) {
		return term.getFlag(context);
	}
	
	public String toString() {
		return "(" + term + ")";
	}
	
	public String toDebugString() {
		return "(" + term.toDebugString() + ")";
	}
	
}
