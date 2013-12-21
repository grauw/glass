package nl.grauw.asm.expressions;

public class Add extends Operator {
	
	private Expression augend;
	private Expression addend;
	
	public Add(Expression augend, Expression addend) {
		this.augend = augend;
		this.addend = addend;
	}
	
	public String toString() {
		return "" + augend + " + " + addend;
	}
	
	public String toDebugString() {
		return "{" + augend.toDebugString() + " + " + addend.toDebugString() + "}";
	}
	
}
