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
	public int getInteger(Context context) {
		return term1.getInteger(context) + term2.getInteger(context);
	}
	
	@Override
	public boolean isRegister(Context context) {
		if (term1.isRegister(context)) {
			Register register = term1.getRegister(context);
			return register == Register.IX || register == Register.IY;
		}
		return false;
	}
	
	@Override
	public Register getRegister(Context context) {
		if (term1.isRegister(context)) {
			Register register = term1.getRegister(context);
			if (register == Register.IX || register == Register.IY)
				return new Register(register, term2);
		}
		throw new EvaluationException("Not a register.");
	}
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
