package nl.grauw.asm.expressions;

public class Complement extends UnaryOperator {
	
	public Complement(Expression term) {
		super(term);
	}
	
	@Override
	public int getInteger(Context context) {
		return ~term.getInteger(context);
	}
	
	@Override
	public String getSymbol() {
		return "~";
	}
	
}
