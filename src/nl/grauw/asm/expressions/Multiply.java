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
	public int getInteger() {
		return term1.getInteger() * term2.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "*";
	}
	
}
