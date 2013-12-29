package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public abstract class Directive {
	
	public abstract void register(Scope scope, Line line);
	
}
