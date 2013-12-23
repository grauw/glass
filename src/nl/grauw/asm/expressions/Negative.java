package nl.grauw.asm.expressions;

public class Negative extends UnaryOperator {
	
	public Negative(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger() {
		return -term.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "-";
	}
	
}
