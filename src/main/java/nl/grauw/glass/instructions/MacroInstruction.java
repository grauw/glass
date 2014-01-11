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

import java.util.List;

import nl.grauw.glass.Line;
import nl.grauw.glass.ParameterScope;
import nl.grauw.glass.Scope;
import nl.grauw.glass.Source;
import nl.grauw.glass.expressions.Expression;
import nl.grauw.glass.expressions.Identifier;

public class MacroInstruction extends Instruction {
	
	private final Expression parameters;
	private final Source source;
	
	public MacroInstruction(Expression parameters, Source source) {
		this.parameters = parameters;
		this.source = source;
		
		Expression parameter = parameters != null ? parameters.getElement(0) : null;
		for (int i = 0; parameter != null; i++) {
			if (!(parameter instanceof Identifier))
				throw new ArgumentException("Parameter must be an identifier.");
			parameter = parameters.getElement(i);
		}
	}
	
	@Override
	public List<Line> expand(Line line) {
		Scope parameterScope = new ParameterScope(source.getScope(), parameters, line.getArguments());
		List<Line> lines = super.expand(line);
		List<Line> lineCopies = source.getLineCopies(parameterScope);
		for (Line lineCopy : lineCopies) {
			lineCopy.register(parameterScope);
			lineCopy.register(line.getScope());
		}
		for (Line lineCopy : lineCopies)
			lines.addAll(lineCopy.expand());
		return lines;
	}
	
	@Override
	public InstructionObject createObject(Expression arguments, Scope context) {
		return new Empty.EmptyObject(context);
	}
	
}
