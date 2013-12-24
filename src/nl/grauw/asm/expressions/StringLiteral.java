package nl.grauw.asm.expressions;

public class StringLiteral extends Literal {
	
	private final String string;
	
	public StringLiteral(String string) {
		this.string = string;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	public boolean isInteger() {
		return string.length() == 1;
	}
	
	@Override
	public int getInteger() {
		if (string.length() != 1)
			throw new EvaluationException("Can not evaluate strings of more than 1 character to integer.");
		return string.codePointAt(0);
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
