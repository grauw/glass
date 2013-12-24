package nl.grauw.asm.expressions;

public class NotEquals extends BinaryOperator {
	
	public NotEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() != term2.getInteger() ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return "!=";
	}
	
}
