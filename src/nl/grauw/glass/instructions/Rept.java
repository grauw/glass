package nl.grauw.glass.instructions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.AssemblyException;
import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.ContextLiteral;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.IntegerLiteral;
import nl.grauw.glass.expressions.Schema;

public class Rept extends Instruction {
	
	public static Schema ARGUMENTS_N = new Schema(Schema.INTEGER);
	public static Schema ARGUMENTS_N_ID = new Schema(Schema.INTEGER, Schema.IDENTIFIER);
	public static Schema ARGUMENTS_N_ID_N = new Schema(Schema.INTEGER, Schema.IDENTIFIER, Schema.INTEGER);
	public static Schema ARGUMENTS_N_ID_N_N = new Schema(Schema.INTEGER, Schema.IDENTIFIER, Schema.INTEGER, Schema.INTEGER);
	
	private final Source source;
	
	public Rept(Source source) {
		this.source = source;
	}
	
	public List<Line> expand(Line line) {
		Expression arguments = line.getArguments();
		if (ARGUMENTS_N.check(arguments))
			return expand(line, arguments.getElement(0).getInteger(), null, 0, 1);
		if (ARGUMENTS_N_ID.check(arguments))
			return expand(line, arguments.getElement(0).getInteger(), arguments.getElement(1), 0, 1);
		if (ARGUMENTS_N_ID_N.check(arguments))
			return expand(line, arguments.getElement(0).getInteger(), arguments.getElement(1),
					arguments.getElement(2).getInteger(), 1);
		if (ARGUMENTS_N_ID_N_N.check(arguments))
			return expand(line, arguments.getElement(0).getInteger(), arguments.getElement(1),
					arguments.getElement(2).getInteger(), arguments.getElement(3).getInteger());
		throw new ArgumentException();
	}
	
	public List<Line> expand(Line line, int count, Expression parameter, int start, int step) {
		if (count < 0)
			throw new ArgumentException("Repetition count must be 0 or greater.");
		List<Line> lines = new ArrayList<Line>();
		for (int i = 0, counter = start; i < count; i++, counter += step) {
			Scope parameterScope = new ParameterScope(line.getScope(), parameter,
					parameter != null ? new IntegerLiteral(counter) : null);
			List<Line> lineCopies = source.getLineCopies(parameterScope);
			for (Line lineCopy : lineCopies)
				lineCopy.register(parameterScope);
			for (Line lineCopy : lineCopies)
				lines.addAll(lineCopy.expand());
			line.getScope().addLabel(Integer.toString(i), new ContextLiteral(parameterScope));
		}
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments) {
		throw new AssemblyException("Not implemented.");
	}
	
}