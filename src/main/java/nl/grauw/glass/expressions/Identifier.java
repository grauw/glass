package nl.grauw.glass.expressions;

import nl.grauw.glass.Scope;

public class Identifier extends Passthrough {

	private final String name;
	private final Context context;

	public Identifier(String name, Context context) {
		this.name = name;
		this.context = context;
	}

	@Override
	public Identifier copy(Context context) {
		return new Identifier(name, ((Scope)context).getParent());
	}

	public String getName() {
		return name;
	}

	@Override
	public Expression resolve() {
		Expression flagOrRegister = FlagOrRegister.getByName(name);
		return flagOrRegister != null ? flagOrRegister : context.getSymbol(name);
	}

	public String toString() {
		return name;
	}

	public String toDebugString() {
		return toString();
	}

}
