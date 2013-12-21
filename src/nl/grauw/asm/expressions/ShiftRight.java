package nl.grauw.asm.expressions;

public class ShiftRight extends Operator {
	
	private Expression term1;
	private Expression term2;
	
	public ShiftRight(Expression term1, Expression term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public String toString() {
		return "" + term1 + " >> " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " >> " + term2.toDebugString() + "}";
	}
	
}
