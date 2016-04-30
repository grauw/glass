package nl.grauw.glass.expressions;

public class Identifier extends Passthrough {
	
	private final String name;
	private final Context context;
	
	public Identifier(String name, Context context) {
		this.name = name;
		this.context = context;
	}
	
	@Override
	public Identifier copy(Context context) {
		return new Identifier(name, context);
	}
	
	public String getName() {
		return name;
	}
	
	public boolean exists() {
		return context.hasSymbol(name);
	}
	
	@Override
	public Expression resolve() {
		return context.getSymbol(name);
	}
	
	@Override
	public boolean isRegister() {
		Register register = Register.getByName(name);
		return register != null || exists() && super.isRegister();
	}
	
	@Override
	public Register getRegister() {
		Register register = Register.getByName(name);
		return register != null ? register : super.getRegister();
	}
	
	@Override
	public boolean isFlag() {
		Flag flag = Flag.getByName(name);
		return flag != null || exists() && super.isFlag();
	}
	
	@Override
	public Flag getFlag() {
		Flag flag = Flag.getByName(name);
		return flag != null ? flag : super.getFlag();
	}
	
	@Override
	public boolean isGroup() {
		return exists() && super.isGroup();
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
