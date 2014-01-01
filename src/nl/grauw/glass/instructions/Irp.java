package nl.grauw.glass.instructions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Schema;

public class Irp extends Instruction {
	
	private final Source source;
	
	public Irp(Source source) {
		this.source = source;
	}
	
	public List<Line> expand(Line line) {
		Expression arguments = line.getArguments();
		if (arguments == null || !Schema.IDENTIFIER.check(arguments.getElement()))
			throw new ArgumentException();
		
		List<Line> lines = new ArrayList<Line>();
		Expression parameter = arguments.getElement();
		for (int i = 0; (arguments = arguments.getNext()) != null; i++) {
			Scope parameterScope = new ParameterScope(line.getScope(), parameter, arguments.getElement());
			
			List<Line> lineCopies = source.getLineCopies(parameterScope);
			for (Line lineCopy : lineCopies)
				lineCopy.register(parameterScope);
			for (Line lineCopy : lineCopies)
				lines.addAll(lineCopy.expand());
			line.getScope().addLabel(Integer.toString(i), parameterScope);
		}
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		throw new AssemblyException("Not implemented.");
	}
	
}
