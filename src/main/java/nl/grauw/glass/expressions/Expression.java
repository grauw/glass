package nl.grauw.glass.expressions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.instructions.InstructionFactory;

public abstract class Expression {

	public abstract String toDebugString();

	public abstract Expression copy(Context context);

	public abstract boolean is(Expression type);

	public Expression get(Expression type) {
		return new ErrorLiteral(new EvaluationException("Not of type " + type)).get(type);
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

	public Expression getHead() {
		return this;
	}

	public Expression getTail() {
		return null;
	}

	public List<Expression> getFlatList() {
		List<Expression> list = new ArrayList<>();
		addToFlatList(list);
		return list;
	}

	private void addToFlatList(List<Expression> list) {
		Expression tail = this;
		while (tail.is(Type.SEQUENCE)) {
			Expression sequence = tail.get(Type.SEQUENCE);
			sequence.getHead().addToFlatList(list);
			tail = sequence.getTail();
		}
		list.add(tail);
	}

	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}

	public String getHexValue() {
		int value = getInteger();
		String string = Integer.toHexString(Math.abs(value)).toUpperCase();
		return (value < 0 ? "-" : "") + (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}

}
