package nl.grauw.glass.directives;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public class Equ extends Directive {

	@Override
	public void register(Scope scope, Line line) {
		if (line.getLabels().size() == 0)
			throw new AssemblyException("Equ without label.");
		for (String label : line.getLabels())
			scope.addSymbol(label, line.getArguments());
	}

}
