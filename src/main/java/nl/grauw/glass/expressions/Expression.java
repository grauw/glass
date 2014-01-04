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

import java.util.ArrayList;
import java.util.List;

public abstract class Expression {
	
	public abstract String toDebugString();
	
	public abstract Expression copy(Context context);
	
	public boolean isInteger() {
		return false;
	}
	
	public int getInteger() {
		throw new EvaluationException("Not an integer.");
	}
	
	public boolean isString() {
		return false;
	}
	
	public String getString() {
		throw new EvaluationException("Not a string.");
	}
	
	public boolean isRegister() {
		return false;
	}
	
	public Register getRegister() {
		throw new EvaluationException("Not a register.");
	}
	
	public boolean isFlag() {
		return false;
	}
	
	public Flag getFlag() {
		throw new EvaluationException("Not a flag.");
	}
	
	public boolean isGroup() {
		return false;
	}
	
	public int getAddress() {
		int address = getInteger();
		if (address < 0 || address >= 0x10000)
			throw new EvaluationException("Address out of range: " + Integer.toHexString(address) + "H");
		return address;
	}
	
	public List<Expression> getList() {
		List<Expression> list = new ArrayList<>();
		addToList(list);
		return list;
	}

	protected void addToList(List<Expression> list) {
		list.add(this);
	}
	
	public Expression getElement() {
		return getElement(0);
	}
	
	public Expression getElement(int index) {
		return index == 0 ? this : null;
	}
	
	public Expression getNext() {
		return null;
	}
	
	public String getHexValue() {
		String string = Integer.toHexString(getInteger()).toUpperCase();
		return (string.charAt(0) >= 'A' && string.charAt(0) <= 'F' ? "0" : "") + string + "H";
	}
	
}
