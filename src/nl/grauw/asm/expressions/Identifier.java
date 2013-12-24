package nl.grauw.asm.expressions;

public class Identifier extends Expression {
	
	private final String name;
	
	public Identifier(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Expression resolve(Context context) {
		return context.getLabel(name);
	}
	
	@Override
	public boolean isInteger(Context context) {
		return resolve(context).isInteger();
	}
	
	@Override
	public int getInteger(Context context) {
		return resolve(context).getInteger();
	}
	
	@Override
	public boolean isRegister(Context context) {
		return Register.getByName(name) != null || resolve(context).isRegister();
	}
	
	@Override
	public Register getRegister(Context context) {
		Register register = Register.getByName(name);
		return register != null ? register : resolve(context).getRegister();
	}
	
	@Override
	public boolean isFlag(Context context) {
		return Flag.getByName(name) != null || resolve(context).isFlag();
	}
	
	@Override
	public Flag getFlag(Context context) {
		Flag flag = Flag.getByName(name);
		return flag != null ? flag : resolve(context).getFlag();
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
