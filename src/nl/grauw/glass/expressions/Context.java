package nl.grauw.glass.expressions;

public interface Context {
	
	public Expression getLabel(String name);
	
	public boolean hasLabel(String name);
	
	public int getAddress();
	
}
