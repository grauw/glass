package nl.grauw.glass.expressions;

public class CharacterLiteral extends Expression {

	private final char character;

	public CharacterLiteral(char character) {
		this.character = character;
	}

	@Override
	public CharacterLiteral copy(Context context) {
		return this;
	}

	public char getCharacter() {
		return character;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.INTEGER);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.INTEGER))
			return IntegerLiteral.of(character);
		return super.get(type);
	}

	public String toString() {
		String escaped = Character.toString(character);
		escaped = escaped.replace("\\", "\\\\");
		escaped = escaped.replace("\'", "\\\'");
		escaped = escaped.replace("\0", "\\0");
		escaped = escaped.replace("\7", "\\a");
		escaped = escaped.replace("\t", "\\t");
		escaped = escaped.replace("\n", "\\n");
		escaped = escaped.replace("\f", "\\f");
		escaped = escaped.replace("\r", "\\r");
		escaped = escaped.replace("\33", "\\e");
		return "'" + escaped + "'";
	}

	public String toDebugString() {
		return toString();
	}

}
