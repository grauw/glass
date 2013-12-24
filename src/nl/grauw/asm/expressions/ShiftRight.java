package nl.grauw.asm.expressions;

public class ShiftRight extends BinaryOperator {
	
	public ShiftRight(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public int getInteger(Context context) {
		return term1.getInteger(context) >> term2.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return ">>";
	}
	
}
