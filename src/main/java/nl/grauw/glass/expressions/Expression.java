package nl.grauw.glass.expressions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.instructions.InstructionFactory;

public abstract class Expression {

	public abstract String toDebugString();

	public abstract Expression copy(Context context);

	public abstract boolean is(Type type);

	public Expression resolve() {
		return this;
	}

	public int getInteger() {
		throw new EvaluationException("Not an integer.");
	}

	public String getString() {
		throw new EvaluationException("Not a string.");
	}

	public Register getRegister() {
		throw new EvaluationException("Not a register.");
	}

	public Flag getFlag() {
		throw new EvaluationException("Not a flag.");
	}

	public Identifier getAnnotation() {
		return null;
	}

	public Expression getAnnotee() {
		return this;
	}

	public InstructionFactory getInstruction() {
		throw new EvaluationException("Not an instruction.");
	}

	public Context getContext() {
		throw new EvaluationException("Not a context.");
	}

	public SectionContext getSectionContext() {
		throw new EvaluationException("Not a context.");
	}

	public List<Expression> getList() {
		List<Expression> list = new ArrayList<>();
		addToList(list);
		return list;
	}

	protected void addToList(List<Expression> list) {
		list.add(this);
	}

	public Expression getElement() {
		return getElement(0);
	}

	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}

	public Expression getNext() {
		return null;
	}

	public String getHexValue() {
		int value = getInteger();
		String string = Integer.toHexString(Math.abs(value)).toUpperCase();
		return (value < 0 ? "-" : "") + (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}

}
