package nl.grauw.glass.expressions;

public class Index extends Passthrough {

	private final Expression sequence;
	private final Expression index;

	public Index(Expression sequence, Expression index) {
		this.sequence = sequence;
		this.index = index;
	}

	@Override
	public Index copy(Context context) {
		return new Index(sequence.copy(context), index.copy(context));
	}

	public Expression getSequence() {
		return sequence;
	}

	public Expression getIndex() {
		return index;
	}

	@Override
	public Expression resolve() {
		int index = this.index.get(Type.INTEGER).getInteger();
		Expression tail = sequence;
		while (index > 0 && tail.is(Type.SEQUENCE)) {
			tail = tail.get(Type.SEQUENCE).getTail();
			index--;
		}
		if (index != 0)
			return new ErrorLiteral(new EvaluationException("Index out of bounds."));
		if (tail.is(Type.SEQUENCE))
			return tail.get(Type.SEQUENCE).getHead();
		return tail;
	}

	public String toString() {
		return "" + sequence + "[" + index + "]";
	}

	public String toDebugString() {
		return "{" + sequence.toDebugString() + "[" + index.toDebugString() + "]}";
	}

}
