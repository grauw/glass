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
package nl.grauw.glass.instructions;

import java.util.ArrayList;
import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.Scope;
import nl.grauw.glass.expressions.Expression;

public abstract class Instruction {
	
	public List<Line> expand(Line line) {
		List<Line> lines = new ArrayList<Line>();
		lines.add(line);
		return lines;
	}
	
	public InstructionObject createObject(Expression arguments, Scope scope) {
		return createObject(arguments);
	}
	
	public abstract InstructionObject createObject(Expression arguments);
	
}
