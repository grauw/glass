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
	public boolean isInteger() {
		return true;
	}

	@Override
	public int getInteger() {
		throw exception;
	}

	@Override
	public boolean isString() {
		return true;
	}

	@Override
	public String getString() {
		throw exception;
	}

	@Override
	public boolean isRegister() {
		return true;
	}

	@Override
	public Register getRegister() {
		throw exception;
	}

	@Override
	public boolean isFlag() {
		return true;
	}

	@Override
	public Flag getFlag() {
		throw exception;
	}

	@Override
	public boolean isGroup() {
		return true;
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
	public boolean isInstruction() {
		return true;
	}

	@Override
	public InstructionFactory getInstruction() {
		throw exception;
	}

	@Override
	public boolean isContext() {
		return true;
	}

	@Override
	public Context getContext() {
		throw exception;
	}

	@Override
	public boolean isSectionContext() {
		return true;
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
