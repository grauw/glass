package nl.grauw.asm.expressions;

public class Negative extends UnaryOperator {
	
	public Negative(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger(Context context) {
		return -term.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "-";
	}
	
}
