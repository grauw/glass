package nl.grauw.asm.expressions;

public class Positive extends UnaryOperator {
	
	public Positive(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger() {
		return +term.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
