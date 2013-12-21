package nl.grauw.asm.expressions;

public class Identifier extends Expression {
	
	String name;
	
	public Identifier(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
