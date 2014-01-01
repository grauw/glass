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

import java.util.List;

public class Sequence extends BinaryOperator {
	
	public Sequence(Expression value, Expression tail) {
		super(value, tail);
	}
	
	public Expression getValue() {
		return term1;
	}
	
	public Expression getTail() {
		return term2;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Sequence(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		throw new EvaluationException("Can not evaluate sequence to integer.");
	}
	
	@Override
	protected void addToList(List<Expression> list) {
		term1.addToList(list);
		Expression tail = term2;
		while (tail != null) {
			tail.getElement().addToList(list);
			tail = tail.getNext();
		}
	}
	
	@Override
	public Expression getElement(int index) {
		return index == 0 ? term1 : term2.getElement(index - 1);
	}
	
	@Override
	public Expression getNext() {
		return term2;
	}
	
	@Override
	public String getSymbol() {
		return ",";
	}
	
	public String toString() {
		return "" + term1 + ", " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + ", " + term2.toDebugString() + "}";
	}
	
}
