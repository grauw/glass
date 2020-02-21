package nl.grauw.glass.expressions;

public abstract class Passthrough extends Expression {

	@Override
	public boolean is(Type type) {
		return resolve().is(type);
	}

	@Override
	public Expression get(Type type) {
		return resolve().get(type);
	}

}
