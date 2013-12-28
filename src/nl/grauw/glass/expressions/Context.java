package nl.grauw.glass.expressions;

public interface Context {
	
	public Expression getLabel(String name);
	
	public int getAddress();
	
}
