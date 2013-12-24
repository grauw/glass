package nl.grauw.asm.expressions;

public interface SchemaType {
	public abstract boolean check(Expression argument, Context context);
}