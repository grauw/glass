package nl.grauw.glass.expressions;

public class ErrorLiteral extends Literal {

	private final EvaluationException exception;

	public ErrorLiteral(EvaluationException exception) {
		this.exception = exception;
	}

	@Override
	public ErrorLiteral copy(Context context) {
		return this;
	}

	@Override
	public Expression resolve() {
		throw exception;
	}

	@Override
	public boolean is(Type type) {
		return true;
	}

	@Override
	public Expression get(Type type) {
		throw exception;
	}

	public String toString() {
		return exception.getMessage();
	}

	public String toDebugString() {
		return toString();
	}

}
