package nl.grauw.asm.expressions;

public abstract class Expression {
	
	public abstract String toDebugString();
	
	public abstract int evaluateInteger();
	
	public Register evaluateRegister() {
		throw new EvaluationException("Not a register.");
	}
	
}
