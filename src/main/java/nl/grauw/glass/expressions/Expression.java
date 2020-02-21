package nl.grauw.glass.expressions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.instructions.InstructionFactory;

public abstract class Expression {

	public abstract String toDebugString();

	public abstract Expression copy(Context context);

	public abstract boolean is(Type type);

	public Expression get(Type type) {
		return new ErrorLiteral(new EvaluationException("Not of type " + type)).get(type);
	}

	public Expression resolve() {
		return this;
	}

	public int getInteger() {
		if (is(Type.INTEGER))
			return get(Type.INTEGER).getInteger();
		throw new EvaluationException("Not an integer.");
	}

	public String getString() {
		if (is(Type.STRING))
			return get(Type.STRING).getString();
		throw new EvaluationException("Not a string.");
	}

	public Register getRegister() {
		if (is(Type.REGISTER)) {
			Expression register = get(Type.REGISTER);
			if (register instanceof Register)
				return (Register)register;
		}
		throw new EvaluationException("Not a register.");
	}

	public Flag getFlag() {
		if (is(Type.FLAG)) {
			Expression flag = get(Type.FLAG);
			if (flag instanceof Flag)
				return (Flag)flag;
		}
		throw new EvaluationException("Not a flag.");
	}

	public Identifier getAnnotation() {
		if (is(Type.ANNOTATION))
			return get(Type.ANNOTATION).getAnnotation();
		throw new EvaluationException("Not an annotation.");
	}

	public Expression getAnnotee() {
		if (is(Type.ANNOTATION))
			return get(Type.ANNOTATION).getAnnotee();
		throw new EvaluationException("Not an annotation.");
	}

	public InstructionFactory getInstruction() {
		if (is(Type.INSTRUCTION))
			return get(Type.INSTRUCTION).getInstruction();
		throw new EvaluationException("Not an instruction.");
	}

	public Context getContext() {
		if (is(Type.CONTEXT))
			return get(Type.CONTEXT).getContext();
		throw new EvaluationException("Not a context.");
	}

	public SectionContext getSectionContext() {
		if (is(Type.SECTIONCONTEXT))
			return get(Type.SECTIONCONTEXT).getSectionContext();
		throw new EvaluationException("Not a section context.");
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
