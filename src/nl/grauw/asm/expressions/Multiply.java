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
	public int getInteger(Context context) {
		return term1.getInteger(context) * term2.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "*";
	}
	
}
