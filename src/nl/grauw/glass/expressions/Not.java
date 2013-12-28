package nl.grauw.glass.expressions;

public class Not extends UnaryOperator {
	
	public Not(Expression term) {
		super(term);
	}
	
	@Override
	public Expression copy(Context context) {
		return new Not(term.copy(context));
	}
	
	@Override
	public int getInteger() {
		return term.getInteger() == 0 ? -1 : 0;
	}
	
	@Override
	public String getSymbol() {
		return "!";
	}
	
}
