package nl.grauw.asm;

import java.util.HashMap;
import java.util.Map;

import nl.grauw.asm.expressions.Expression;
import nl.grauw.asm.expressions.Identifier;
import nl.grauw.asm.expressions.Sequence;
import nl.grauw.asm.instructions.ArgumentException;

public class Scope {
	
	Scope parent;
	Map<String, Expression> variables = new HashMap<>();
	
	public Scope() {
	}
	
	public Scope(Scope parent) {
		this.parent = parent;
	}
	
	public void addLabel(String label, Expression value) {
		if (variables.get(label) != null)
			throw new AssemblyException("Can not redefine label.");
		variables.put(label, value);
	}
	
	public void redefineLabel(String label, Expression value) {
		variables.put(label, value);
	}
	
	public boolean hasLabel(String label) {
		return variables.get(label) == null || parent != null && parent.hasLabel(label);
	}
	
	public Expression getLabel(String label) {
		Expression value = variables.get(label);
		if (value != null)
			return value;
		if (parent != null)
			return parent.getLabel(label);
		throw new AssemblyException("Label not found: " + label);
	}
	
	public void addParameters(Expression parameters, Expression arguments) {
		while (parameters != null) {
			if (arguments == null)
				throw new ArgumentException("Not enough arguments.");
			Expression parameter = parameters instanceof Sequence ? ((Sequence)parameters).getValue() : parameters;
			Expression argument = arguments instanceof Sequence ? ((Sequence)arguments).getValue() : arguments;
			
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			
			addLabel(((Identifier)parameter).getName(), argument);
			
			parameters = parameter instanceof Sequence ? ((Sequence)parameter).getTail() : null;
			arguments = argument instanceof Sequence ? ((Sequence)argument).getTail() : null;
		}
		if (arguments != null)
			throw new ArgumentException("Too many arguments.");
	}
	
}
