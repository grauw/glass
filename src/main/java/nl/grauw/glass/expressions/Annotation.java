package nl.grauw.glass.expressions;

import nl.grauw.glass.expressions.ExpressionBuilder.ExpressionError;

public class Annotation extends BinaryOperator {
	
	public Annotation(Expression annotation, Expression annotee) {
		super(annotation, annotee);
		if (!(annotation instanceof Identifier))
			throw new ExpressionError("Annotation left hand side must be an identifier: " + annotation);
	}
	
	public Identifier getAnnotation() {
		return (Identifier)term1;
	}
	
	public Expression getAnnotee() {
		return term2;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Annotation(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public boolean isInteger() {
		return false;
	}
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Not an integer.");
	}
	
	@Override
	public String getLexeme() {
		return " ";
	}
	
	public String toString() {
		return "" + term1 + " " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " " + term2.toDebugString() + "}";
	}
	
}
