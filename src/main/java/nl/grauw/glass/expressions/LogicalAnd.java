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

public class LogicalAnd extends BinaryOperator {
	
	public LogicalAnd(Expression term1, Expression term2) {
		super(term1, term2);
	}
	
	@Override
	public Expression copy(Context context) {
		return new LogicalAnd(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		int value1 = term1.getInteger();
		return value1 == 0 ? value1 : term2.getInteger();
	}
	
	@Override
	public String getLexeme() {
		return "&&";
	}
	
}
