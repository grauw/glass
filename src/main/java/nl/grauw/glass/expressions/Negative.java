package nl.grauw.glass.expressions;

public class Negative extends UnaryOperator {
	
	public Negative(Expression term) {
		super(term);
	}
	
	@Override
	public Expression copy(Context context) {
		return new Negative(term.copy(context));
	}
	
	@Override
	public int getInteger() {
		return -term.getInteger();
	}
	
	@Override
	public String getLexeme() {
		return "-";
	}
	
}
