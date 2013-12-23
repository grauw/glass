package nl.grauw.asm.expressions;

public class Not extends UnaryOperator {
	
	public Not(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger() {
		return term.getInteger() == 0 ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return "!";
	}
	
}
