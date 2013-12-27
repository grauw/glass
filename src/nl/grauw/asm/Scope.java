package nl.grauw.asm;

import java.util.HashMap;
import java.util.Map;

import nl.grauw.asm.expressions.Expression;

public class Scope {
	
	Map<String, Expression> variables = new HashMap<>();
	
	public void addLabel(String label, Expression value) {
		if (variables.get(label) != null)
			throw new RuntimeException("Can not redefine label.");
		variables.put(label, value);
	}
	
	public void redefineLabel(String label, Expression value) {
		variables.put(label, value);
	}
	
	public boolean hasLabel(String label) {
		return variables.get(label) == null;
	}
	
	public Expression getLabel(String label) {
		Expression value = variables.get(label);
		if (value == null)
			throw new RuntimeException("Label not found: " + label);
		return value;
	}
	
}
