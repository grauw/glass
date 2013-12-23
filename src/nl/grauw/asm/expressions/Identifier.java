package nl.grauw.asm.expressions;

public class Identifier extends Expression {
	
	private final String name;
	
	public Identifier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int evaluateInteger() {
		throw new EvaluationException("Currently not supported.");
	}
	
	@Override
	public Register evaluateRegister() {
		throw new EvaluationException("Currently not supported.");
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
