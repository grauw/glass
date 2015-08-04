package nl.grauw.glass;

import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.instructions.ArgumentException;

public class ParameterScope extends Scope {
	
	public ParameterScope(Scope parent, Expression parameters, Expression arguments) {
		super(parent);
		
		while (parameters != null) {
			if (arguments == null)
				throw new ArgumentException("Not enough arguments.");
			Expression parameter = parameters.getElement();
			Expression argument = arguments.getElement();
			
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			
			addSymbol(((Identifier)parameter).getName(), argument);
			
			parameters = parameters.getNext();
			arguments = arguments.getNext();
		}
		if (arguments != null)
			throw new ArgumentException("Too many arguments.");
	}
	
}
