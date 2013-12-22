package nl.grauw.asm.expressions;

public class Sequence extends Expression {
	
	private final Expression value;
	private final Expression tail;
	
	public Sequence(Expression value, Expression tail) {
		this.value = value;
		this.tail = tail;
	}
	
	public Expression getTail() {
		return tail;
	}
	
	public String toString() {
		return "" + value + (tail != null ? ", " + tail : "");
	}
	
	public String toDebugString() {
		return "{" + value.toDebugString() + (tail != null ? ", " + tail.toDebugString() : "") + "}";
	}
	
}
