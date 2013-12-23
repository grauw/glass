package nl.grauw.asm.expressions;

public class Negative extends UnaryOperator {
	
	public Negative(Expression term) {
		super(term);
	}
	
	@Override
	public int evaluateInteger() {
		return -term.evaluateInteger();
	}
	
	public String toString() {
		return "-" + term;
	}
	
	public String toDebugString() {
		return "-" + term.toDebugString();
	}
	
}
