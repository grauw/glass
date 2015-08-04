package nl.grauw.glass.expressions;

import nl.grauw.glass.instructions.Ds;

public class SectionContextLiteral extends ContextLiteral {
	
	private final Ds sectionContext;
	
	public SectionContextLiteral(Context context, Ds sectionContext) {
		super(context);
		this.sectionContext = sectionContext;
	}
	
	public Ds getSectionContext() {
		return sectionContext;
	}
	
}
