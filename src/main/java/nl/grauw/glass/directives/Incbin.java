package nl.grauw.glass.directives;

import java.nio.file.Path;
import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public class Incbin extends Directive {

	private final List<Path> basePaths;

	public Incbin(List<Path> basePaths) {
		this.basePaths = basePaths;
	}

	@Override
	public void register(Scope scope, Line line) {
		line.setInstruction(new nl.grauw.glass.instructions.Incbin(basePaths));
		super.register(scope, line);
	}

}
