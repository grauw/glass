package nl.grauw.glass.expressions;

public class ErrorLiteral extends Expression {

	private final EvaluationException exception;

	public ErrorLiteral(EvaluationException exception) {
		this.exception = exception;
	}

	@Override
	public ErrorLiteral copy(Context context) {
		return this;
	}

	@Override
	public boolean is(Expression type) {
		return true;
	}

	@Override
	public Expression get(Expression type) {
		throw exception;
	}

	public String toString() {
		return exception.getMessage();
	}

	public String toDebugString() {
		return toString();
	}

}
