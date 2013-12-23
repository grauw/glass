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
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
