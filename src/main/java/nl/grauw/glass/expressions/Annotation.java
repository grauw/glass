/*
 * Copyright 2014 Laurens Holst
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
