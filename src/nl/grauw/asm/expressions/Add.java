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
	public Expression copy(Context context) {
		return new Add(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() + term2.getInteger();
	}
	
	@Override
	public boolean isRegister() {
		if (term1.isRegister()) {
			Register register = term1.getRegister();
			return register.isIndex() && register.isPair();
		}
		return false;
	}
	
	@Override
	public Register getRegister() {
		if (term1.isRegister()) {
			Register register = term1.getRegister();
			if (register.isIndex() && register.isPair())
				return new Register(register, new Add(register.getIndexOffset(), term2));
		}
		throw new EvaluationException("Not a register.");
	}
	
	@Override
	public String getSymbol() {
		return "+";
	}
	
}
