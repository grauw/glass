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
	public int getInteger() {
		return term1.getInteger() - term2.getInteger();
	}
	
	@Override
	public boolean isRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.getRegister();
			return register == Register.IX || register == Register.IY;
		}
		return false;
	}
	
	@Override
	public Register getRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.getRegister();
			if (register == Register.IX || register == Register.IY)
				return new Register(register, new Negative(term2));
		}
		throw new EvaluationException("Not a register.");
	}
	
	@Override
	public String getSymbol() {
		return "-";
	}
	
}
