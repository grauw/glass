package nl.grauw.asm.expressions;

public class Schema {
	
	private Type[] types;
	
	public Schema(Type... types) {
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
	
	public abstract static class Type {
		public abstract boolean check(Expression argument);
	}
	
	public static Type ANY = new Any();
	public static class Any extends Type {
		public boolean check(Expression argument) {
			return true;
		}
	}
	
}
