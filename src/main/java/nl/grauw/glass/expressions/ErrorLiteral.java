package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.InstructionFactory;

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
	public int getInteger() {
		throw exception;
	}

	@Override
	public String getString() {
		throw exception;
	}

	@Override
	public Register getRegister() {
		throw exception;
	}

	@Override
	public Flag getFlag() {
		throw exception;
	}

	@Override
	public Identifier getAnnotation() {
		throw exception;
	}

	@Override
	public Expression getAnnotee() {
		throw exception;
	}

	@Override
	public InstructionFactory getInstruction() {
		throw exception;
	}

	@Override
	public Context getContext() {
		throw exception;
	}

	@Override
	public SectionContext getSectionContext() {
		throw exception;
	}

	public String toString() {
		return exception.getMessage();
	}

	public String toDebugString() {
		return toString();
	}

}
