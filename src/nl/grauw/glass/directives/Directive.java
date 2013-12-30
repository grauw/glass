package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.ContextLiteral;

public abstract class Directive {
	
	public void register(Scope scope, Line line) {
		if (line.getLabel() != null)
			scope.addLabel(line.getLabel(), new ContextLiteral(line.getScope()));
	}
	
}
