package nl.grauw.asm.expressions;

public class Complement extends UnaryOperator {
	
	public Complement(Expression term) {
		super(term);
	}
	
	@Override
	public int evaluateInteger() {
		return ~term.evaluateInteger();
	}
	
	public String toString() {
		return "~" + term;
	}
	
	public String toDebugString() {
		return "~" + term.toDebugString();
	}
	
}
