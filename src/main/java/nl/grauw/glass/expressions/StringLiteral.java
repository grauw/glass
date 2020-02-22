package nl.grauw.glass.expressions;

public class StringLiteral extends Expression {

	private final String string;

	public StringLiteral(String string) {
		this.string = string;
	}

	@Override
	public StringLiteral copy(Context context) {
		return this;
	}

	public String getString() {
		return string;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.STRING) ||
			(type.is(Type.INTEGER) && string.length() == 1) ||
			(type.is(Type.SEQUENCE) && string.length() > 1);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.STRING))
			return this;
		if (type.is(Type.INTEGER) && string.length() == 1)
			return new CharacterLiteral(string.charAt(0));
		if (type.is(Type.SEQUENCE) && string.length() > 1) {
			Expression tail = new CharacterLiteral(string.charAt(string.length() - 1));
			for (int i = string.length() - 2; i >= 0; i--)
				tail = new Sequence(new CharacterLiteral(string.charAt(i)), tail);
			return tail;
		}
		return super.get(type);
	}

	public String toString() {
		String escaped = string;
		escaped = escaped.replace("\\", "\\\\");
		escaped = escaped.replace("\"", "\\\"");
		escaped = escaped.replace("\0", "\\0");
		escaped = escaped.replace("\7", "\\a");
		escaped = escaped.replace("\t", "\\t");
		escaped = escaped.replace("\n", "\\n");
		escaped = escaped.replace("\f", "\\f");
		escaped = escaped.replace("\r", "\\r");
		escaped = escaped.replace("\33", "\\e");
		return "\"" + escaped + "\"";
	}

	public String toDebugString() {
		return toString();
	}

}
