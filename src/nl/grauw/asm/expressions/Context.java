package nl.grauw.asm.expressions;

public interface Context {
	
	public Expression getLabel(String label);
	
	public int getAddress();
	
}
