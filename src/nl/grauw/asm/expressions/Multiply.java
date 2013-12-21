package nl.grauw.asm.expressions;

public class Multiply extends Operator {
	
	private Expression multiplicand;
	private Expression multiplier;
	
	public Multiply(Expression multiplicand, Expression multiplier) {
		this.multiplicand = multiplicand;
		this.multiplier = multiplier;
	}
	
	public String toString() {
		return "" + multiplicand + " * " + multiplier;
	}
	
	public String toDebugString() {
		return "{" + multiplicand.toDebugString() + " * " + multiplier.toDebugString() + "}";
	}
	
}
