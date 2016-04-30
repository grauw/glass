package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.InstructionFactory;

public class Member extends Operator {
	
	private final Expression object;
	private final Identifier subject;
	
	public Member(Expression object, Identifier subject) {
		this.object = object;
		this.subject = subject;
	}
	
	public Expression getObject() {
		return object;
	}
	
	public Expression getSubject() {
		return subject;
	}
	
	@Override
	public Member copy(Context context) {
		return new Member(object.copy(context), subject.copy(context));
	}
	
	public Expression resolve() {
		if (!object.isContext())
			throw new EvaluationException("Object not found.");
		return object.getContext().getSymbol(subject.getName());
	}
	
	@Override
	public boolean isInteger() {
		return resolve().isInteger();
	}
	
	@Override
	public int getInteger() {
		return resolve().getInteger();
	}
	
	@Override
	public boolean isRegister() {
		return resolve().isRegister();
	}
	
	@Override
	public Register getRegister() {
		return resolve().getRegister();
	}
	
	@Override
	public boolean isFlag() {
		return resolve().isFlag();
	}
	
	@Override
	public Flag getFlag() {
		return resolve().getFlag();
	}
	
	@Override
	public boolean isGroup() {
		return resolve().isGroup();
	}
	
	@Override
	public boolean isInstruction() {
		return resolve().isInstruction();
	}
	
	@Override
	public InstructionFactory getInstruction() {
		return resolve().getInstruction();
	}
	
	@Override
	public boolean isContext() {
		return resolve().isContext();
	}
	
	@Override
	public Context getContext() {
		return resolve().getContext();
	}
	
	@Override
	public String toString() {
		return "" + object + "." + subject;
	}
	
	public String toDebugString() {
		return "{" + object.toDebugString() + "." + subject.toDebugString() + "}";
	}
	
}
