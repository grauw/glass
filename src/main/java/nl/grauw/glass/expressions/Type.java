package nl.grauw.glass.expressions;

public class Type {

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
	public String toString() {
		return name;
	}

}
