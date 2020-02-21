package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Include extends InstructionFactory {

	public static Schema ARGUMENTS = new Schema(Schema.STRING);
	public static Schema ARGUMENTS_ONCE = new Schema(new Schema.IsAnnotation(Schema.STRING));

	private final Source source;

	public Include(Source source) {
		this.source = source;
	}

	public void expand(Line line, List<Line> lines) {
		super.expand(line, lines);
		source.expand(lines);
	}

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return new Empty.EmptyObject(context);
		if (ARGUMENTS_ONCE.check(arguments)) {
			String annotation = arguments.getAnnotation().getName();
			if ("once".equals(annotation) || "ONCE".equals(annotation))
				return new Empty.EmptyObject(context);
		}
		throw new ArgumentException();
	}

}
