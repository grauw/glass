package nl.grauw.asm.expressions;

public class Sequence extends BinaryOperator {
	
	private final Expression value;
	private final Expression tail;
	
	public Sequence(Expression value, Expression tail) {
		this.value = value;
		this.tail = tail;
	}
	
	public Expression getValue() {
		return value;
	}
	
	public Expression getTail() {
		return tail;
	}
	
	@Override
	public int evaluateInteger() {
		throw new EvaluationException("Can not evaluate sequence to integer.");
	}
	
	public String toString() {
		return "" + value + ", " + tail;
	}
	
	public String toDebugString() {
		return "{" + value.toDebugString() + ", " + tail.toDebugString() + "}";
	}
	
}
