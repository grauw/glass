package nl.grauw.glass;

import nl.grauw.glass.expressions.Equals;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.instructions.ArgumentException;

public class ParameterScope extends Scope {

	public ParameterScope(Scope parent, Expression parameters, Expression arguments) {
		super(parent);

		while (parameters != null) {
			Expression parameter = parameters.getHead();
			Expression argument;

			if (parameter instanceof Equals equals) {
				argument = arguments != null ? arguments.getHead() : equals.getTerm2();
				parameter = equals.getTerm1();
			} else {
				if (arguments == null)
					throw new ArgumentException("Not enough arguments.");
				argument = arguments.getHead();
			}

			if (parameter instanceof Identifier identifier) {
				addSymbol(identifier.getName(), argument);

				parameters = parameters.getTail();
				if (arguments != null)
					arguments = arguments.getTail();
			} else {
				throw new ArgumentException("Parameter must be an identifier.");
			}
		}
		if (arguments != null)
			throw new ArgumentException("Too many arguments.");
	}

}
