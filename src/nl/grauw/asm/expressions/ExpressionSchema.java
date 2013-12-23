package nl.grauw.asm.expressions;

public class ExpressionSchema {
	
	private Type[] types;
	
	public static ExpressionSchema NONE = new ExpressionSchema();
	
	public ExpressionSchema(Type... types) {
		this.types = types;
	}
	
	public boolean check(Expression arguments) {
		for (int i = 0; arguments != null && i < types.length; i++) {
			if (!types[i].check(arguments instanceof Sequence ? ((Sequence)arguments).getValue() : arguments))
				return false;
			arguments = arguments instanceof Sequence ? ((Sequence)arguments).getTail() : null;
		}
		return arguments == null;
	}
	
	public abstract class Type {
		public abstract boolean check(Expression argument);
	}
	
	public class Any {
		public boolean check(Expression argument) {
			return true;
		}
	}
	
}
