package nl.grauw.asm.expressions;

public class Identifier extends Expression {
	
	private final String name;
	
	public Identifier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean isInteger() {
		return false;  // TODO recur
	}
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Currently not supported.");
	}
	
	@Override
	public boolean isRegister() {
		return Register.getByName(name) != null;  // TODO recur
	}
	
	@Override
	public Register getRegister() {
		Register register = Register.getByName(name);
		if (register == null)
			throw new EvaluationException("Not a register.");
		return register;
	}
	
	@Override
	public boolean isFlag() {
		return Flag.getByName(name) != null;  // TODO recur
	}
	
	@Override
	public Flag getFlag() {
		Flag flag = Flag.getByName(name);
		if (flag == null)
			throw new EvaluationException("Not a flag.");
		return flag;
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
