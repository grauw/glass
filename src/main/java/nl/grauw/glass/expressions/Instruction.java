package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.InstructionFactory;

public class Instruction extends Expression {

	private final InstructionFactory instruction;
	private final Context context;

	public Instruction(InstructionFactory instruction, Context context) {
		this.instruction = instruction;
		this.context = context;
	}

	@Override
	public Instruction copy(Context context) {
		return new Instruction(instruction, context);
	}

	@Override
	public InstructionFactory getInstruction() {
		return instruction;
	}

	@Override
	public boolean is(Type type) {
		return type == Type.INSTRUCTION || type == Type.CONTEXT;
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INSTRUCTION)
			return this;
		if (type == Type.CONTEXT)
			return new ContextLiteral(context);
		return super.get(type);
	}

	public String toString() {
		return "instruction";
	}

	public String toDebugString() {
		return toString();
	}

}
