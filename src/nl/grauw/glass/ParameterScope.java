package nl.grauw.glass;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.Sequence;
import nl.grauw.glass.instructions.ArgumentException;

public class ParameterScope extends Scope {
	
	public ParameterScope(Scope parent, Expression parameters, Expression arguments) {
		super(parent);
		
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
