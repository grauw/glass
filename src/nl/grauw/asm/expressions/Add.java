package nl.grauw.asm.expressions;

public class Add extends BinaryOperator {
	
	public Add(Expression augend, Expression addend) {
		super(augend, addend);
	}
	
	public Expression getAugend() {
		return term1;
	}
	
	public Expression getAddend() {
		return term2;
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() + term2.evaluateInteger();
	}
	
	public String toString() {
		return "" + term1 + " + " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " + " + term2.toDebugString() + "}";
	}
	
}
