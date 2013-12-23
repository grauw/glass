package nl.grauw.asm.expressions;

public class Complement extends UnaryOperator {
	
	public Complement(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger() {
		return ~term.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "~";
	}
	
}
