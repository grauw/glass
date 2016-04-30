package nl.grauw.glass.expressions;

import nl.grauw.glass.expressions.ExpressionBuilder.ExpressionError;

public class Annotation extends Operator {
	
	private final Expression annotation;
	private final Expression annotee;
	
	public Annotation(Expression annotation, Expression annotee) {
		if (!(annotation instanceof Identifier))
			throw new ExpressionError("Annotation left hand side must be an identifier: " + annotation);
		this.annotation = annotation;
		this.annotee = annotee;
	}
	
	public Identifier getAnnotation() {
		return (Identifier)annotation;
	}
	
	public Expression getAnnotee() {
		return annotee;
	}
	
	@Override
	public Annotation copy(Context context) {
		return new Annotation(annotation.copy(context), annotee.copy(context));
	}
	
	public String toString() {
		return "" + annotation + " " + annotee;
	}
	
	public String toDebugString() {
		return "{" + annotation.toDebugString() + " " + annotee.toDebugString() + "}";
	}
	
}
