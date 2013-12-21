package nl.grauw.asm.expressions;

public class Complement extends Operator {
	
	private Expression term;
	
	public Complement(Expression term) {
		this.term = term;
	}
	
	public String toString() {
		return "~" + term;
	}
	
	public String toDebugString() {
		return this.toString();
	}
	
}
