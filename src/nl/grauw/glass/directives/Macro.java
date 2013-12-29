package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.instructions.Macro.Factory;

public class Macro extends Directive {
	
	private final Source source;
	
	public Macro(Source source) {
		this.source = source;
	}
	
	@Override
	public void register(Scope scope, Line line) {
		new Factory(line.getLabel(), line.getArguments(), source).register(scope);
	}
	
}
