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

public class StringLiteral extends Literal {
	
	private final String string;
	
	public StringLiteral(String string) {
		this.string = string;
	}
	
	@Override
	public Expression copy(Context context) {
		return this;
	}
	
	@Override
	public boolean isInteger() {
		return string.length() == 1;
	}
	
	@Override
	public int getInteger() {
		if (string.length() != 1)
			throw new EvaluationException("Can not evaluate strings of more than 1 character to integer.");
		return string.codePointAt(0);
	}
	
	@Override
	public boolean isString() {
		return true;
	}
	
	public String getString() {
		return string;
	}
	
	@Override
	protected void addToList(List<Expression> list) {
		for (int i = 0, length = string.length(); i < length; i++)
			list.add(new CharacterLiteral(string.charAt(i)));
	}
	
	public String toString() {
		String escaped = string;
		escaped = escaped.replace("\\", "\\\\");
		escaped = escaped.replace("\"", "\\\"");
		escaped = escaped.replace("\0", "\\0");
		escaped = escaped.replace("\7", "\\a");
		escaped = escaped.replace("\t", "\\t");
		escaped = escaped.replace("\n", "\\n");
		escaped = escaped.replace("\f", "\\f");
		escaped = escaped.replace("\r", "\\r");
		escaped = escaped.replace("\33", "\\e");
		return "\"" + escaped + "\"";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
