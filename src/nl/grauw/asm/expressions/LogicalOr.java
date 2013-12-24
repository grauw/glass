package nl.grauw.asm.expressions;

public class LogicalOr extends BinaryOperator {
	
	public LogicalOr(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int getInteger(Context context) {
		int value1 = term1.getInteger(context);
		return value1 != 0 ? value1 : term2.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "||";
	}
	
}
