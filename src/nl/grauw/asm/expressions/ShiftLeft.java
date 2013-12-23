package nl.grauw.asm.expressions;

public class ShiftLeft extends BinaryOperator {
	
	public ShiftLeft(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() << term2.evaluateInteger();
	}
	
	public String toString() {
		return "" + term1 + " << " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " << " + term2.toDebugString() + "}";
	}
	
}
