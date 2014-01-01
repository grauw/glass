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

public class IntegerLiteral extends Literal {
	
	public static final IntegerLiteral ZERO = new IntegerLiteral(0);
	public static final IntegerLiteral ONE = new IntegerLiteral(1);
	
	private final int value;
	
	public IntegerLiteral(int value) {
		this.value = value;
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
		return value;
	}
	
	public String toString() {
		String string = Integer.toHexString(value).toUpperCase();
		return (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}
	
	public String toDebugString() {
		return toString();
	}
	
}
