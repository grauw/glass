package nl.grauw.asm.expressions;

public class Complement extends UnaryOperator {
	
	public Complement(Expression term) {
		super(term);
	}
	
	@Override
	public int evaluateInteger() {
		return ~term.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "~";
	}
	
}
