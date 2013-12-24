package nl.grauw.asm.expressions;

public abstract class Expression {
	
	public abstract String toDebugString();
	
	public abstract boolean isInteger();
	
	public abstract int getInteger();
	
	public boolean isRegister() {
		return false;
	}
	
	public Register getRegister() {
		throw new EvaluationException("Not a register.");
	}
	
	public boolean isFlag() {
		return false;
	}
	
	public Flag getFlag() {
		throw new EvaluationException("Not a flag.");
	}
	
	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}
	
}
