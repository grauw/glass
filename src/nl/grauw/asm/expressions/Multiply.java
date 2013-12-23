package nl.grauw.asm.expressions;

public class Multiply extends BinaryOperator {
	
	public Multiply(Expression multiplicand, Expression multiplier) {
		super(multiplicand, multiplier);
	}
	
	public Expression getMultiplicand() {
		return term1;
	}
	
	public Expression getMultiplier() {
		return term2;
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() * term2.evaluateInteger();
	}
	
	@Override
	public String getSymbol() {
		return "*";
	}
	
}
