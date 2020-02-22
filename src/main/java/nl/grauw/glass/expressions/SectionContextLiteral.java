package nl.grauw.glass.expressions;

public class SectionContextLiteral extends ContextLiteral {

	private final SectionContext sectionContext;

	public SectionContextLiteral(Context context, SectionContext sectionContext) {
		super(context);
		this.sectionContext = sectionContext;
	}

	@Override
	public SectionContext getSectionContext() {
		return sectionContext;
	}

	@Override
	public boolean is(Expression type) {
		return type.is(Type.SECTIONCONTEXT) || super.is(type);
	}

	@Override
	public Expression get(Expression type) {
		if (type.is(Type.SECTIONCONTEXT))
			return this;
		return super.get(type);
	}

}
