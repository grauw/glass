package nl.grauw.asm.expressions;

public class Positive extends UnaryOperator {
	
	public Positive(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger(Context context) {
		return +term.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
