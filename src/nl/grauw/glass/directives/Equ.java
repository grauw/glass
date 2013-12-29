package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public class Equ extends Directive {
	
	@Override
	public void register(Scope scope, Line line) {
		scope.addLabel(line.getLabel(), line.getArguments());
	}
	
}
