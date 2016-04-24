package nl.grauw.glass.expressions;

public class IfElse extends Operator {
	
	private final Expression condition;
	private final Expression trueTerm;
	private final Expression falseTerm;
	
	public IfElse(Expression condition, Expression trueTerm, Expression falseTerm) {
		this.condition = condition;
		this.trueTerm = trueTerm;
		this.falseTerm = falseTerm;
	}
	
	public Expression getCondition() {
		return condition;
	}
	
	public Expression getTrueTerm() {
		return trueTerm;
	}
	
	public Expression getFalseTerm() {
		return falseTerm;
	}
	
	public boolean isTrue() {
		return condition.getInteger() != 0;
	}
	
	public Expression getTerm() {
		return isTrue() ? trueTerm : falseTerm;
	}
	
	@Override
	public Expression copy(Context context) {
		return new IfElse(condition.copy(context), trueTerm.copy(context), falseTerm.copy(context));
	}
	
	@Override
	public boolean isInteger() {
		return (trueTerm.isInteger() && falseTerm.isInteger()) || getTerm().isInteger();
	}
	
	@Override
	public int getInteger() {
		return getTerm().getInteger();
	}
	
	@Override
	public boolean isString() {
		return (trueTerm.isString() && falseTerm.isString()) || getTerm().isString();
	}
	
	@Override
	public String getString() {
		return getTerm().getString();
	}
	
	@Override
	public boolean isRegister() {
		return (trueTerm.isRegister() && falseTerm.isRegister()) || getTerm().isRegister();
	}
	
	@Override
	public Register getRegister() {
		return getTerm().getRegister();
	}
	
	@Override
	public boolean isFlag() {
		return (trueTerm.isFlag() && falseTerm.isFlag()) || getTerm().isFlag();
	}
	
	@Override
	public Flag getFlag() {
		return getTerm().getFlag();
	}
	
	@Override
	public boolean isContext() {
		return (trueTerm.isContext() && falseTerm.isContext()) || getTerm().isContext();
	}
	
	@Override
	public Context getContext() {
		return getTerm().getContext();
	}
	
	@Override
	public String toString() {
		return "" + condition + " ? " + trueTerm + " : " + falseTerm;
	}
	
	@Override
	public String toDebugString() {
		return "{" + condition.toDebugString() + " ? " + trueTerm.toDebugString() + " : " + falseTerm.toDebugString() + "}";
	}
	
}
