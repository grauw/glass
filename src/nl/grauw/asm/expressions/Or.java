package nl.grauw.asm.expressions;

public class Or extends BinaryOperator {
	
	public Or(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() | term2.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "|";
	}
	
}
