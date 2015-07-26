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

public class Subtract extends BinaryOperator {
	
	public Subtract(Expression minuend, Expression subtrahend) {
		super(minuend, subtrahend);
	}
	
	public Expression getMinuend() {
		return term1;
	}
	
	public Expression getSubtrahend() {
		return term2;
	}
	
	@Override
	public Expression copy(Context context) {
		return new Subtract(term1.copy(context), term2.copy(context));
	}
	
	@Override
	public int getInteger() {
		return term1.getInteger() - term2.getInteger();
	}
	
	@Override
	public boolean isRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.getRegister();
			return register.isIndex() && register.isPair();
		}
		return false;
	}
	
	@Override
	public Register getRegister() {
		if (term1.isRegister() && term2.isInteger()) {
			Register register = term1.getRegister();
			if (register.isIndex() && register.isPair())
				return new Register(register, new Subtract(register.getIndexOffset(), term2));
		}
		throw new EvaluationException("Not a register.");
	}
	
	@Override
	public String getLexeme() {
		return "-";
	}
	
}
