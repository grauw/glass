package nl.grauw.asm.expressions;

public class StringLiteral extends Literal {
	
	private final String string;
	
	public StringLiteral(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	public String toString() {
		return "\"" + string + "\"";
	}
	
	public String toDebugString() {
		return this.toString();
	}
	
}
