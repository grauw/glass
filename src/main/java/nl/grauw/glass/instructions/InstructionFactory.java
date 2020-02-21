package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public abstract class InstructionFactory {

	public void expand(Line line, List<Line> lines) {
		lines.add(line);
	}

	public abstract InstructionObject createObject(Scope context, Expression arguments);

}
