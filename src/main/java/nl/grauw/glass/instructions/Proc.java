package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Proc extends InstructionFactory {
	
	public static Schema ARGUMENTS = new Schema();
	
	private final Source source;
	
	public Proc(Source source) {
		this.source = source;
	}
	
	public void expand(Line line, List<Line> lines) {
		Expression arguments = line.getArguments();
		if (!ARGUMENTS.check(arguments))
			throw new ArgumentException();
		
		super.expand(line, lines);
		List<Line> lineCopies = source.getLineCopies(line.getScope());
		for (Line lineCopy : lineCopies)
			lineCopy.register(line.getScope());
		for (Line lineCopy : lineCopies)
			lineCopy.expand(lines);
	}
	
	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		return new Empty.EmptyObject(context);
	}
	
}
