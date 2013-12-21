package nl.grauw.asm.expressions;

public class Not extends Operator {
	
	private Expression term;
	
	public Not(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "!" + term;
	}
	
}
