package nl.grauw.asm.expressions;

public class Positive extends Operator {
	
	private Expression term;
	
	public Positive(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "+" + term;
	}
	
}
