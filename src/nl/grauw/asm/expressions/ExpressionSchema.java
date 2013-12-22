package nl.grauw.asm.expressions;

public class ExpressionSchema {
	
	private Type[] types;
	
	public static ExpressionSchema NONE = new ExpressionSchema();
	
	public ExpressionSchema(Type... types) {
		this.types = types;
	}
	
	public boolean check(Expression arguments) {
		Expression current = arguments;
		for (Type type : types) {
			if (current == null)
				return false;
			
			type.check(current);
			
			current = current instanceof Sequence ? ((Sequence)current).getTail() : null;
		}
		if (current != null)
			return false;
		return true;
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
