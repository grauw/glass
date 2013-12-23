package nl.grauw.asm.expressions;

public class Add extends Operator {
	
	private final Expression augend;
	private final Expression addend;
	
	public Add(Expression augend, Expression addend) {
		this.augend = augend;
		this.addend = addend;
	}
	
	public Expression getAugend() {
		return augend;
	}
	
	public Expression getAddend() {
		return addend;
	}
	
	@Override
	public int evaluateInteger() {
		return augend.evaluateInteger() + addend.evaluateInteger();
	}
	
	public String toString() {
		return "" + augend + " + " + addend;
	}
	
	public String toDebugString() {
		return "{" + augend.toDebugString() + " + " + addend.toDebugString() + "}";
	}
	
}
