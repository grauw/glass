package nl.grauw.asm.expressions;

public class Positive extends UnaryOperator {
	
	public Positive(Expression term) {
		super(term);
	}
	
	@Override
	public int evaluateInteger() {
		return +term.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
