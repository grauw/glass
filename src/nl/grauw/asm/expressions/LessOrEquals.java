package nl.grauw.asm.expressions;

public class LessOrEquals extends BinaryOperator {
	
	public LessOrEquals(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public Expression copy(Context context) {
		return new LessOrEquals(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() <= term2.getInteger() ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return "<=";
	}
	
}
