package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public abstract class Directive {
	
	public void register(Scope scope, Line line) {
		for (String label : line.getLabels())
			scope.addSymbol(label, line.getScope());
	}
	
}
