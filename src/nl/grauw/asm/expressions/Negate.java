package nl.grauw.asm.expressions;

public class Negate extends Operator {
	
	private Expression term;
	
	public Negate(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "-" + term;
	}
	
}
