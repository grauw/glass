package nl.grauw.asm.expressions;

public class Xor extends BinaryOperator {
	
	public Xor(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() ^ term2.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "^";
	}
	
}
