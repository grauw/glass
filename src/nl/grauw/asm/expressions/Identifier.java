package nl.grauw.asm.expressions;

public class Identifier extends Expression {
	
	private Context context;
	private final String name;
	
	public Identifier(String name, Context context) {
		this.name = name;
		this.context = context;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Identifier(name, context);
	}
	
	public Expression resolve() {
		return context.getLabel(name);
	}
	
	@Override
	public boolean isInteger() {
		return resolve().isInteger();
	}
	
	@Override
	public int getInteger() {
		return resolve().getInteger();
	}
	
	@Override
	public boolean isRegister() {
		return Register.getByName(name) != null;
//		return Register.getByName(name) != null || resolve().isRegister();
	}
	
	@Override
	public Register getRegister() {
		Register register = Register.getByName(name);
		if (register != null)
			return register;
		throw new EvaluationException("Currently not supported.");
//		return register != null ? register : resolve().getRegister();
	}
	
	@Override
	public boolean isFlag() {
		return Flag.getByName(name) != null;
//		return Flag.getByName(name) != null || resolve().isFlag();
	}
	
	@Override
	public Flag getFlag() {
		Flag flag = Flag.getByName(name);
		if (flag != null)
			return flag;
		throw new EvaluationException("Currently not supported.");
//		return flag != null ? flag : resolve().getFlag();
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
