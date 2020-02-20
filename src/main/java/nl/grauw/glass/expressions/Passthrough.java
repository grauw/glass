package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.InstructionFactory;

public abstract class Passthrough extends Expression {

	@Override
	public boolean is(Type type) {
		return resolve().is(type);
	}

	@Override
	public int getInteger() {
		return resolve().getInteger();
	}

	@Override
	public String getString() {
		return resolve().getString();
	}

	@Override
	public Register getRegister() {
		return resolve().getRegister();
	}

	@Override
	public Flag getFlag() {
		return resolve().getFlag();
	}

	@Override
	public InstructionFactory getInstruction() {
		return resolve().getInstruction();
	}

	@Override
	public Context getContext() {
		return resolve().getContext();
	}

	@Override
	public SectionContext getSectionContext() {
		return resolve().getSectionContext();
	}

}
