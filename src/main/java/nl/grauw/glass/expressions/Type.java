package nl.grauw.glass.expressions;

public class Type extends Expression {

	public static final Type INTEGER = new Type("Integer");
	public static final Type STRING = new Type("String");
	public static final Type REGISTER = new Type("Register");
	public static final Type FLAG = new Type("Flag");
	public static final Type GROUP = new Type("Group");
	public static final Type ANNOTATION = new Type("Annotation");
	public static final Type INSTRUCTION = new Type("Instruction");
	public static final Type CONTEXT = new Type("Context");
	public static final Type SECTIONCONTEXT = new Type("SectionContext");
	public static final Type SEQUENCE = new Type("Sequence");

	private String name;

	Type(String name) {
		this.name = name;
	}

	@Override
	public Expression copy(Context context) {
		return this;
	}

	@Override
	public boolean is(Expression type) {
		return type == this;
	}

	@Override
	public Expression get(Expression type) {
		if (type == this)
			return this;
		return super.get(type);
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toDebugString() {
		return toString();
	}

}
