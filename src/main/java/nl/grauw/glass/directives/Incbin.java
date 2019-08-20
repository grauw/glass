package nl.grauw.glass.directives;

import java.nio.file.Path;
import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;

public class Incbin extends Directive {
	
	private final Path sourcePath;
	private final List<Path> includePaths;
	
	public Incbin(Path sourcePath, List<Path> includePaths) {
		this.sourcePath = sourcePath;
		this.includePaths = includePaths;
	}
	
	@Override
	public void register(Scope scope, Line line) {
		line.setInstruction(new nl.grauw.glass.instructions.Incbin(sourcePath, includePaths));
		super.register(scope, line);
	}
	
}
