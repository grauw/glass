package nl.grauw.glass.directives;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public class Equ extends Directive {
	
	@Override
	public void register(Scope scope, Line line) {
		if (line.getLabel() == null)
			throw new AssemblyException("Equ without label.");
		scope.addLabel(line.getLabel(), line.getArguments());
	}
	
}
