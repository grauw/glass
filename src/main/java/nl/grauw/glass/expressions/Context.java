package nl.grauw.glass.expressions;

public interface Context {

	public Expression getSymbol(String name);

	public Expression getLocalSymbol(String name);

	public boolean hasSymbol(String name);

	public boolean hasLocalSymbol(String name);

	public Expression getAddress();

}
