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

public class CharacterLiteral extends Literal {
	
	private final char character;
	
	public CharacterLiteral(char character) {
		this.character = character;
	}
	
	public char getCharacter() {
		return character;
	}
	
	@Override
	public Expression copy(Context context) {
		return this;
	}
	
	@Override
	public boolean isInteger() {
		return true;
	}
	
	@Override
	public int getInteger() {
		return character;
	}
	
	public String toString() {
		String escaped = Character.toString(character);
		escaped = escaped.replace("\\", "\\\\");
		escaped = escaped.replace("\'", "\\\'");
		escaped = escaped.replace("\0", "\\0");
		escaped = escaped.replace("\7", "\\a");
		escaped = escaped.replace("\t", "\\t");
		escaped = escaped.replace("\n", "\\n");
		escaped = escaped.replace("\f", "\\f");
		escaped = escaped.replace("\r", "\\r");
		escaped = escaped.replace("\33", "\\e");
		return "'" + escaped + "'";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
