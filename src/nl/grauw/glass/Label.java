package nl.grauw.glass;

import nl.grauw.glass.expressions.Context;

public class Label {
	
	private final String name;
	private final Context context;
	
	public Label(String name, Context context) {
		this.name = name;
		this.context = context;
	}
	
	public Label(Label other, Context context) {
		this(other.name, context);
	}
	
	public String getName() {
		return name;
	}
	
	public int getValue() {
		return context.getAddress();
	}
	
	public String toString() {
		return name;
	}
	
}
