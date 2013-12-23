package nl.grauw.asm.expressions;

public class Subtract extends BinaryOperator {
	
	public Subtract(Expression minuend, Expression subtrahend) {
		super(minuend, subtrahend);
	}
	
	public Expression getMinuend() {
		return term1;
	}
	
	public Expression getSubtrahend() {
		return term2;
	}
	
	@Override
	public int evaluateInteger() {
		return term1.evaluateInteger() - term2.evaluateInteger();
	}
	
	@Override
	public boolean isRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.evaluateRegister();
			return register == Register.IX || register == Register.IY;
		}
		return false;
	}
	
	@Override
	public Register evaluateRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.evaluateRegister();
			if (register == Register.IX || register == Register.IY)
				return new Register(register, -term2.evaluateInteger());
		}
		throw new EvaluationException("Not a register.");
	}
	
	@Override
	public String getSymbol() {
		return "-";
	}
	
}
