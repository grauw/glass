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

public class Complement extends UnaryOperator {
	
	public Complement(Expression term) {
		super(term);
	}
	
	@Override
	public Expression copy(Context context) {
		return new Complement(term.copy(context));
	}
	
	@Override
	public int getInteger() {
		return ~term.getInteger();
	}
	
	@Override
	public String getSymbol() {
		return "~";
	}
	
}
