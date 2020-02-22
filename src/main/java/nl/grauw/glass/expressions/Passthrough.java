package nl.grauw.glass.expressions;

public abstract class Passthrough extends Expression {

	public abstract Expression resolve();

	@Override
	public boolean is(Expression type) {
		return resolve().is(type);
	}

	@Override
	public Expression get(Expression type) {
		return resolve().get(type);
	}

}
