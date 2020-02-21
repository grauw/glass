package nl.grauw.glass.directives;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;

public class Include extends Directive {

	private final Source source;

	public Include(Source source) {
		this.source = source;
	}

	@Override
	public Directive copy(Scope scope) {
		return new Include(source.copy(scope.getParent()));
	}

	@Override
	public void register(Scope scope, Line line) {
		line.setInstruction(new nl.grauw.glass.instructions.Include(source));
		super.register(scope, line);
		source.register();
	}

}
