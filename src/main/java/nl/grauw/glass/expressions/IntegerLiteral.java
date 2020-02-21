package nl.grauw.glass.expressions;

public class IntegerLiteral extends Literal {

	public static final IntegerLiteral ZERO = new IntegerLiteral(0);
	public static final IntegerLiteral ONE = new IntegerLiteral(1);
	public static final IntegerLiteral MINUSONE = new IntegerLiteral(-1);

	private final int value;

	public IntegerLiteral(int value) {
		this.value = value;
	}

	public static IntegerLiteral of(int value) {
		return new IntegerLiteral(value);
	}

	public static IntegerLiteral of(boolean value) {
		return value ? MINUSONE : ZERO;
	}

	@Override
	public IntegerLiteral copy(Context context) {
		return this;
	}

	@Override
	public int getInteger() {
		return value;
	}

	@Override
	public boolean is(Type type) {
		return type == Type.INTEGER;
	}

	@Override
	public Expression get(Type type) {
		if (type == Type.INTEGER)
			return this;
		return super.get(type);
	}

	public String toString() {
		String string = Integer.toHexString(value).toUpperCase();
		return (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}

	public String toDebugString() {
		return toString();
	}

}
