package nl.grauw.asm.expressions;

public class ShiftLeft extends BinaryOperator {
	
	public ShiftLeft(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public Expression copy(Context context) {
		return new ShiftLeft(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() << term2.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "<<";
	}
	
}
