package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Irp extends InstructionFactory {

	private final Source source;

	public Irp(Source source) {
		this.source = source;
	}

	public void expand(Line line, List<Line> lines) {
		Expression arguments = line.getArguments();
		if (arguments == null || !Schema.IDENTIFIER.check(arguments.getHead()))
			throw new ArgumentException();

		super.expand(line, lines);
		Expression parameter = arguments.getHead();
		for (int i = 0; (arguments = arguments.getTail()) != null; i++) {
			Scope parameterScope = new ParameterScope(line.getScope(), parameter, arguments.getHead());

			// set up the number symbol
			line.getScope().addSymbol(Integer.toString(i), parameterScope);
			Line rept = line.copy(parameterScope);
			rept.setInstruction(Empty.INSTANCE);
			lines.add(rept);  // so that the parameterScope address gets initialised

			// copy & expand content
			Source sourceCopy = source.copy(parameterScope);
			sourceCopy.register();
			sourceCopy.expand(lines);
		}
	}

	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		return new Empty.EmptyObject(context);
	}

}
