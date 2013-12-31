package nl.grauw.glass.instructions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;

public class MacroInstruction extends Instruction {
	
	private final Expression parameters;
	private final Source source;
	
	public MacroInstruction(Expression parameters, Source source) {
		this.parameters = parameters;
		this.source = source;
		
		Expression parameter = parameters != null ? parameters.getElement(0) : null;
		for (int i = 0; parameter != null; i++) {
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			parameter = parameters.getElement(i);
		}
	}
	
	@Override
	public List<Line> expand(Line line) {
		Scope parameterScope = new ParameterScope(line.getScope(), parameters, line.getArguments());
		List<Line> lines = new ArrayList<Line>();
		List<Line> lineCopies = source.getLineCopies(parameterScope);
		for (Line lineCopy : lineCopies)
			lineCopy.register(line.getScope());
		for (Line lineCopy : lineCopies)
			lines.addAll(lineCopy.expand());
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		throw new AssemblyException("Not implemented.");
	}
	
}
