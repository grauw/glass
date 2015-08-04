package nl.grauw.glass.instructions;

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;
import nl.grauw.glass.expressions.Schema;
import nl.grauw.glass.expressions.SectionContextLiteral;

public class Section extends Instruction {
	
	public static Schema ARGUMENTS = new Schema(Schema.IDENTIFIER);
	
	private final Source source;
	
	public Section(Source source) {
		this.source = source;
	}
	
	public Source getSource() {
		return source;
	}
	
	@Override
	public List<Line> expand(Line line) {
		if (!ARGUMENTS.check(line.getArguments()))
			throw new ArgumentException();
		
		Expression argument = ((Identifier)line.getArguments()).resolve();
		if (!(argument instanceof SectionContextLiteral))
			throw new ArgumentException("Argument does not reference a section context.");
		
		Ds sectionContext = ((SectionContextLiteral)argument).getSectionContext();
		sectionContext.addSection(this);
		
		source.expand();
		return super.expand(line);
	}
	
	@Override
	public InstructionObject createObject(Scope context, Expression arguments) {
		if (ARGUMENTS.check(arguments))
			return new Empty.EmptyObject(context);
		throw new ArgumentException();
	}
	
}
