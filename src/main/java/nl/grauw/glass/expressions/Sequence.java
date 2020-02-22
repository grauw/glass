package nl.grauw.glass.expressions;

public class Sequence extends BinaryOperator {

	public Sequence(Expression head, Expression tail) {
		super(head, tail);
	}

	@Override
	public Sequence copy(Context context) {
		return new Sequence(term1.copy(context), term2.copy(context));
	}

	@Override
	public Expression getHead() {
		return term1;
	}

	@Override
	public Expression getTail() {
		return term2;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.SEQUENCE);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.SEQUENCE))
			return this;
		return super.get(type);
	}

	@Override
	public Expression getElement(int index) {
		return index == 0 ? term1 : term2.getElement(index - 1);
	}

	@Override
	public String getLexeme() {
		return ",";
	}

	public String toString() {
		return "" + term1 + ", " + term2;
	}

	public String toDebugString() {
		return "{" + term1.toDebugString() + ", " + term2.toDebugString() + "}";
	}

}
