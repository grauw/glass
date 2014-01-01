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

public abstract class BinaryOperator extends Operator {
	
	protected final Expression term1;
	protected final Expression term2;
	
	public abstract String getSymbol();
	
	public BinaryOperator(Expression term1, Expression term2) {
		this.term1 = term1;
		this.term2 = term2;
	}
	
	public Expression getTerm1() {
		return term1;
	}
	
	public Expression getTerm2() {
		return term2;
	}
	
	@Override
	public boolean isInteger() {
		return term1.isInteger() && term2.isInteger();
	}
	
	public String toString() {
		return "" + term1 + " " + getSymbol() + " " + term2;
	}
	
	public String toDebugString() {
		return "{" + term1.toDebugString() + " " + getSymbol() + " " + term2.toDebugString() + "}";
	}
	
}
