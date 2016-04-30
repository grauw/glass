package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.InstructionFactory;

public class Identifier extends Expression {
	
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
	
	public Expression resolve() {
		return context.getSymbol(name);
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
		Register register = Register.getByName(name);
		return register != null || exists() && resolve().isRegister();
	}
	
	@Override
	public Register getRegister() {
		Register register = Register.getByName(name);
		return register != null ? register : resolve().getRegister();
	}
	
	@Override
	public boolean isFlag() {
		Flag flag = Flag.getByName(name);
		return flag != null || exists() && resolve().isFlag();
	}
	
	@Override
	public Flag getFlag() {
		Flag flag = Flag.getByName(name);
		return flag != null ? flag : resolve().getFlag();
	}
	
	@Override
	public boolean isGroup() {
		return exists() && resolve().isGroup();
	}
	
	@Override
	public boolean isInstruction() {
		return resolve().isInstruction();
	}
	
	@Override
	public InstructionFactory getInstruction() {
		return resolve().getInstruction();
	}
	
	@Override
	public boolean isContext() {
		return resolve().isContext();
	}
	
	@Override
	public Context getContext() {
		return resolve().getContext();
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
