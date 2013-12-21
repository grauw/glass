package nl.grauw.asm.expressions;

public class Or extends Operator {
	
	private Expression term1;
	private Expression term2;
	
	public Or(Expression term1, Expression term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public String toString() {
		return "" + term1 + " || " + term2;
	}
	
}
