package nl.grauw.asm.expressions;

public class GreaterOrEquals extends BinaryOperator {
	
	public GreaterOrEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int getInteger(Context context) {
		return term1.getInteger(context) >= term2.getInteger(context) ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return ">=";
	}
	
}
