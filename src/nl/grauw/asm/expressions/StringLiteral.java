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
