package nl.grauw.asm.expressions;

public class IntegerLiteral extends Literal {
	
	public static final IntegerLiteral ZERO = new IntegerLiteral(0);
	
	private final int value;
	
	public IntegerLiteral(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	@Override
	public int getInteger() {
		return getValue();
	}
	
	public String toString() {
		String string = Integer.toHexString(value).toUpperCase();
		return (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
