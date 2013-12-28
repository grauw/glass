package nl.grauw.asm.expressions;

public class Sequence extends BinaryOperator {
	
	public Sequence(Expression value, Expression tail) {
		super(value, tail);
	}
	
	public Expression getValue() {
		return term1;
	}
	
	public Expression getTail() {
		return term2;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Sequence(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Can not evaluate sequence to integer.");
	}
	
	public Expression getElement(int index) {
		return index == 0 ? term1 : term2.getElement(index - 1);
	}
	
	@Override
	public String getSymbol() {
		return ",";
	}
	
	public String toString() {
		return "" + term1 + ", " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + ", " + term2.toDebugString() + "}";
	}
	
}
