package nl.grauw.glass.directives;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;

public class Macro extends Directive {
	
	private final Source source;
	
	public Macro(Source source) {
		this.source = source;
	}
	
	@Override
	public void register(Scope scope, Line line) {
		if (line.getLabel() == null)
			throw new AssemblyException("Macro without label.");
		scope.addInstruction(line.getLabel(), new nl.grauw.glass.instructions.Macro(line.getArguments(), source));
		super.register(scope, line);
	}
	
}
