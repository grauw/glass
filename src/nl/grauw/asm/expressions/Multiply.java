package nl.grauw.asm.expressions;

public class Multiply extends Operator {
	
	private final Expression multiplicand;
	private final Expression multiplier;
	
	public Multiply(Expression multiplicand, Expression multiplier) {
		this.multiplicand = multiplicand;
		this.multiplier = multiplier;
	}
	
	public Expression getMultiplicand() {
		return multiplicand;
	}
	
	public Expression getMultiplier() {
		return multiplier;
	}
	
	public String toString() {
		return "" + multiplicand + " * " + multiplier;
	}
	
	public String toDebugString() {
		return "{" + multiplicand.toDebugString() + " * " + multiplier.toDebugString() + "}";
	}
	
}
