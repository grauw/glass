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

public class ContextLiteral extends Literal {
	
	private final Context context;
	
	public ContextLiteral(Context context) {
		this.context = context;
	}
	
	@Override
	public boolean isContext() {
		return true;
	}
	
	@Override
	public Context getContext() {
		return context;
	}
	
	@Override
	public Expression copy(Context context) {
		return new ContextLiteral(context);
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	@Override
	public int getInteger() {
		return context.getAddress();
	}
	
	public String toString() {
		try {
			return getHexValue();
		} catch (EvaluationException e) {
			return "<" + e.getMessage() + ">";
		}
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
