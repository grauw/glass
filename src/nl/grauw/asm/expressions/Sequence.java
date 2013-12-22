package nl.grauw.asm.expressions;

public class Sequence extends Expression {
	
	private final Expression value;
	private final Sequence tail;
	
	public Sequence(Expression value, Sequence tail) {
		this.value = value;
		this.tail = tail;
	}
	
	public Sequence getTail() {
		return tail;
	}
	
	public String toString() {
		return "" + value + (tail != null ? ", " + tail : "");
	}
	
	public String toDebugString() {
		return "{" + value.toDebugString() + (tail != null ? ", " + tail.toDebugString() : "") + "}";
	}
	
}
